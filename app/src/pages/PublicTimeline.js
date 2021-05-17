import React, { useEffect, useState } from 'react';
import Layout from '../components/Layout';
import axios from 'axios';
import { initiateSocket, socket } from "../components/Socketio";
import { TimelineAction } from "../components/TimelineAction";



export default function PublicTimeline() {
  // three elements: books progress, comment, rate
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [event_data, setData] = useState(null);
  const [refreshPage, setRefreshPage] = useState(false);
  const [bookinfo, setBookInfo] = useState(new Map());
  const userid = sessionStorage.getItem("currentUserID");
  const [relationshipid, setRelationshipId] = useState(null); // store all relationship id (friends id and user id)


  // for socket.io connection and set subcriptions
  useEffect(() => {
    initiateSocket(userid);

    //Topic: to update the page
    // if the user's friends post some new message
    socket.on("updateTimelinePage", (res) => {
      // console.log(res);
      setRefreshPage(true);
    });
    socket.on("refreshReply", (res) => {
      setRefreshPage(true);
    });
  }, []);

  // refresh the page if refreshPage turns true
  useEffect(() => {
    if (refreshPage) {
      fetchEvent();
      setRefreshPage(false);
    }
  }, [refreshPage]);


  function getBookInfoByGoogleID(googlebookid) {

    if (bookinfo.has(googlebookid)) // googlebookid == null means other actions
      return;

    axios.get(`/api/book/${googlebookid}`)
      .then(res => {
        setBookInfo(bookinfo => new Map(bookinfo.set(googlebookid, res.data)));
      })
      .catch(error => {
        console.log("getBookInfoByGoogleID error");
      });
  }

  function fetchEvent() {

    axios.get(`/api/publicTimeline?userids=${relationshipid}`)
      .then(res => {
        setIsLoaded(true);
        setData(res.data);
        res.data.map((t, idx) => {
          if (t.googlebookid !== "null") {
            getBookInfoByGoogleID(t.googlebookid);
          }
        });
      })
      .catch(error => {
        setIsLoaded(true);
        setError(error);
      });
  }

  function getRelationshipId() {

    // query all friends' user id
    axios.get(`/api/relationship/friends/${userid}`)
      .then(res => {

        let userids = userid;
        res.data.map((item) => {
          userids += "," + item.id;
        });
        setRelationshipId(userids);

      })
      .catch(error => {
        setIsLoaded(true);
        setError(error);
      });

  }

  // get friendship when the page first render
  useEffect(() => {

    getRelationshipId();
    if (relationshipid) {
      fetchEvent();
    }

  }, [relationshipid]);

  if (error) {
    return (
      <Layout>
        <div> Error: {error.message}</div>
      </Layout>
    );
  }
  else if (!isLoaded) {
    return (<div><h1>Loading...</h1></div>);
  }
  else {

    return (
      <Layout>
        <div>
          {event_data && event_data.map((timeline_event, idx) => {
            return (
              TimelineAction(timeline_event, bookinfo)
            );
          })}
        </div>
      </Layout>
    );
  }

}
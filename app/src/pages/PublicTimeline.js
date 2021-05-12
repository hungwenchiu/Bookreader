import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import Layout from '../components/Layout';
import RecipeReviewCard from '../components/TimelineEvent';
import axios from 'axios';
import FriendShipEventCard from "../components/FriendshipEventCard";
import {initiateSocket, socket} from "../components/Socketio";



export default function PublicTimeline(){
    // three elements: books progress, comment, rate
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [event_data, setData] = useState(null);
    const [refreshPage, setRefreshPage] = useState(false);
    const [bookinfo, setBookInfo] = useState(new Map());
    const name = sessionStorage.getItem("currentUser");
    const userid = sessionStorage.getItem("currentUserID");
    const [relationshipid, setRelationshipId] = useState(null); // store all relationship id (friends id and user id)


    // for socket.io connection and set subcriptions
    useEffect(() => {
        initiateSocket(userid);

        //Topic: to update the page
        // if the user's friends post some new message
        socket.on("updateTimelinePage", (res) => {
            console.log(res);
            setRefreshPage(true);
        });
    }, []);

    // refresh the page if refreshPage turns true
    useEffect(() => {
       if(refreshPage) {
           fetchEvent();
           setRefreshPage(false);
       }
    }, [refreshPage]);


    function getBookInfoByGoogleID(googlebookid) {

        console.log("googlebookid  ", googlebookid);

        if(bookinfo.has(googlebookid)) // googlebookid == null means other actions
            return;

        axios.get(`/api/book/${googlebookid}`)
            .then(res =>{
                setBookInfo(bookinfo => new Map(bookinfo.set(googlebookid, res.data)));
            })
            .catch( error => {
                console.log("getBookInfoByGoogleID error");
            });
    }

    function fetchEvent() {

        axios.get(`/api/publicTimeline?userids=${relationshipid}`)
            .then(res =>{
                console.log(res.data);
                setIsLoaded(true);
                setData(res.data);
                res.data.map((t, idx) => {
                    if(t.googlebookid !== "null") {
                        // console.log("go to getBookInfoByGoogleID");
                        getBookInfoByGoogleID(t.googlebookid);
                    }
                });
            })
            .catch( error => {
                setIsLoaded(true);
                setError(error);
            });
    }

    function getRelationshipId() {

        // query all friends' user id
        axios.get(`/api/relationship/friends/${userid}`)
            .then(res =>{

                let userids = userid;
                res.data.map((item) => {
                    userids += "," + item.id;
                });
                setRelationshipId(userids);

            })
            .catch( error => {
                setIsLoaded(true);
                setError(error);
            });

    }

    // get friendship when the page first render
    useEffect(() => {

        getRelationshipId();
        console.log(relationshipid);
        if(relationshipid) {
            fetchEvent();
        }

    }, [relationshipid]);

    if(error) {
        return (
            <Layout>
                <div> Error: {error.message}</div>
            </Layout>
        );
    }
    else if(!isLoaded) {
        return (<div><h1>Loading...</h1></div>);
    }
    else {

        return (
            <Layout>
                <div>
                    {/*<InfiniteScroll next={} hasMore={} loader={} dataLength={}*/}
                    {event_data && event_data.map((timeline_event, idx) => {

                        let bookdescription = "";
                        let author = "";
                        let thumbnail = "";

                        // Event action for Friendship
                        if(timeline_event.action === 'Friendship') {
                            return (
                                <FriendShipEventCard
                                    time={timeline_event.time}
                                    content={timeline_event.content}
                                />
                            );
                        }

                        // Event action for books
                        if(bookinfo.has(timeline_event.googlebookid)) {
                            bookdescription = bookinfo.get(timeline_event.googlebookid).description;
                            author = bookinfo.get(timeline_event.googlebookid).author;
                            thumbnail =  bookinfo.get(timeline_event.googlebookid).thumbnail;
                        }

                        return (
                            <RecipeReviewCard username={timeline_event.name}
                                              bookname={timeline_event.bookName}
                                              action={"User Action: " + timeline_event.action}
                                              comment={timeline_event.content}
                                              author={author}
                                              bookdescription={bookdescription}
                                              rate={5}
                                              progress={20}
                                              time={timeline_event.time}
                                              image={thumbnail}
                                              id={timeline_event.id}
                                              key={idx}
                            />
                        );
                    })}
                </div>
            </Layout>
        );
    }

}
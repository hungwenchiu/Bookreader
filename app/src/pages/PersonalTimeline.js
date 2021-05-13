import React, {useEffect,useState} from 'react';
import Layout from '../components/Layout';
import RecipeReviewCard from '../components/TimelineReviewCard';
import axios from 'axios';
import FriendShipEventCard from "../components/FriendshipEventCard";
import {initiateSocket, subscribe} from '../components/Socketio';
import {TimelineAction} from '../components/TimelineAction';

export default function PersonalTimeline(){

    // three elements: books progress, comment, rate
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [event_data, setData] = useState(null);
    // const [isFetching, setIsFetching] = useState(true);
    const [bookinfo, setBookInfo] = useState(new Map());
    const name = sessionStorage.getItem("currentUser");
    const userid = sessionStorage.getItem("currentUserID");

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

        axios.get(`/api/personalTimeline?userid=${userid}`)
            .then(res =>{
                    // const new_data = (event_data) ? event_data.concat(res.data) : ;
                    setIsLoaded(true);
                    setData(res.data);
                    res.data.map((t, idx) => {
                        getBookInfoByGoogleID(t.googlebookid);
                    });
            })
            .catch( error => {
                setIsLoaded(true);
                setError(error);
            });
    }

    // load all timeline event from database
    useEffect(() => {
        fetchEvent();
    }, []);

    // initial socket
    useEffect(() => {
        initiateSocket(userid);
    }, []);



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
                    {event_data && event_data.map((timeline_event) => {

                        return (
                            TimelineAction(timeline_event, bookinfo)
                        );
                    })}
                </div>
            </Layout>
        );
    }

}


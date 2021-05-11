import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import Layout from '../components/Layout';
import RecipeReviewCard from '../components/TimelineEvent';
import axios from 'axios';
import FriendShipEventCard from "../components/FriendshipEventCard";



export default function PublicTimeline(){
    // three elements: books progress, comment, rate
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [event_data, setData] = useState(null);
    // const [event_idx, setEventIdx] = useState(0);
    // const [isFetching, setIsFetching] = useState(true);
    const [bookinfo, setBookInfo] = useState(new Map());
    const name = sessionStorage.getItem("currentUser");
    const userid = sessionStorage.getItem("currentUserID");
    const [relationshipid, setRelationshipId] = useState(null); // store all relationship id (friends id and user id)

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

    // function handleScroll() {
    //     if (window.innerHeight + document.documentElement.scrollTop !== document.documentElement.offsetHeight) return;
    //     setIsFetching(true);
    // }

    // get more timeline event, 5 events for each time
    function fetchEvent() {

        axios.get(`/api/publicTimeline?userids=${relationshipid}`)
            .then(res =>{
                const new_data = (event_data) ? event_data.concat(res.data) : res.data;
                setIsLoaded(true);
                setData(new_data);
                res.data.map((t, idx) => {
                    console.log(typeof t.googlebookid);
                    if(t.googlebookid !== "null")
                    {
                        console.log("go to getBookInfoByGoogleID");
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
        axios.get(`api/relationship/friends/${userid}`)
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
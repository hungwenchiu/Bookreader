import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import Layout from '../components/Layout';
import RecipeReviewCard from '../components/TimelineEvent';
import axios from 'axios';
// import Button from "@material-ui/core/Button";
// import useSocket from  'use-socket.io-client';


export default function PersonalTimeline(){
    // three elements: books progress, comment, rate
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [event_data, setData] = useState(null);
    const [event_idx, setEventIdx] = useState(0);
    const [isFetching, setIsFetching] = useState(true);
    const [bookinfo, setBookInfo] = useState(new Map());
    const name = sessionStorage.getItem("currentUser");

    function getBookInfoByGoogleID(googlebookid) {

        if(bookinfo.has(googlebookid))
            return;

        axios.get(`/api/book/${googlebookid}`)
            .then(res =>{
                setBookInfo(bookinfo => new Map(bookinfo.set(googlebookid, res.data)));
            })
            .catch( error => {
                console.log("getBookInfoByGoogleID error");
            });
    }

    function handleScroll() {
        if (window.innerHeight + document.documentElement.scrollTop !== document.documentElement.offsetHeight) return;
        setIsFetching(true);
    }

    function fetchMoreEvent(name, event_idx) {

        axios.get(`/api/personalTimeline?name=${name}&idx=${event_idx}`)
            .then(res =>{
                    const new_data = (event_data) ? event_data.concat(res.data) : res.data;
                    setIsLoaded(true);
                    setData(new_data);
                    setEventIdx(event_idx + 3);
                    setIsFetching(false);
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
    useEffect(async () => {

        window.addEventListener('scroll', handleScroll);
        if(!isFetching)
            return;
        await fetchMoreEvent(name, event_idx);

        return () => window.removeEventListener('scroll', handleScroll);
    }, [isFetching]);


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

                        if(bookinfo.has(timeline_event.googlebookid)) {
                            bookdescription = bookinfo.get(timeline_event.googlebookid).description;
                            author = bookinfo.get(timeline_event.googlebookid).author;
                            thumbnail =  bookinfo.get(timeline_event.googlebookid).thumbnail;
                        }

                        return (
                            <RecipeReviewCard username={timeline_event.name}
                                              bookname={timeline_event.bookName}
                                              action={"User Action: " + timeline_event.action}
                                              comment={timeline_event.review}
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
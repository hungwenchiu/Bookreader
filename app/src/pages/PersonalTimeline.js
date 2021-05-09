import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import Layout from '../components/Layout';
import RecipeReviewCard from '../components/TimelineEvent';
import axios from 'axios';
import Button from "@material-ui/core/Button";
import useSocket from  'use-socket.io-client';


export default function PersonalTimeline(){
    // three elements: books progress, comment, rate
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [event_data, setData] = useState(null);
    const [event_idx, setEventIdx] = useState(0);
    const [isFetching, setIsFetching] = useState(true);
    const name = sessionStorage.getItem("currentUser");
    // const  [socket]  =  useSocket('http://localhost:9092');
    // const [message, setMessage] = useState(null);

    // socket.connect();
    // console.log(socket);


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

    // useEffect(() => {
    //     socket.on('connect_msg', (message) => console.log(message));
    // }, [message]);

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
                        // console.log(timeline_event);
                        return (
                            <RecipeReviewCard username={timeline_event.name}
                                              bookname={timeline_event.bookName}
                                              action={"User Action: " + timeline_event.action}
                                              comment={timeline_event.review}
                                              rate={timeline_event.rate}
                                              progress={timeline_event.progress}
                                              time={timeline_event.time}
                                              image={timeline_event.img}
                                              id={timeline_event.id}
                                                />
                        );
                    })}
                </div>
            </Layout>
        );
    }

}
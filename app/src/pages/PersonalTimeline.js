import React, {useEffect, useState} from 'react';
import Layout from '../components/Layout'
import RecipeReviewCard from '../components/TimelineEvent'
import axios from 'axios'


export default function PersonalTimeline(){
    // three elements: books progress, comment, rate
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [event_data, setData] = useState(null);
    const name = "Ted"; // TODO

    // load all timeline event from database
    useEffect(() => {

        axios.get(`/api/personalTimeline?name=${name}`)
            .then(res =>{
                setIsLoaded(true);
                setData(res.data);
            })
            .catch( error => {
                setIsLoaded(true);
                setError(error);
        });
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
                    {event_data && event_data.map((timeline_event, idx) => {
                        return (
                            <RecipeReviewCard username={timeline_event.name}
                                              bookname={timeline_event.bookName}
                                              action={"User Action: " + timeline_event.action}
                                              comment={timeline_event.comment}
                                              rate={timeline_event.rate}
                                              progress={timeline_event.progress}
                                              time={timeline_event.time}
                                              image={timeline_event.img}
                                              key={idx}/>
                                              );
                    })}
                </div>
            </Layout>
        );
    }

}
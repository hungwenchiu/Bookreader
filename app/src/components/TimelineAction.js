import FriendShipEventCard from "./FriendshipEventCard";
import React from "react";
import RecipeReviewCard from "./TimelineReviewCard";
import * as PropTypes from "prop-types";
import TimelineProgressCard from "./TimelineProgressCard";

export const TimelineAction = (timeline_event, bookinfo) => {

    let bookdescription = "";
    let author = "";
    let thumbnail = "";

    if(timeline_event.action === 'Friendship') {
        return (
            <FriendShipEventCard
                time={timeline_event.time}
                content={timeline_event.content}
            />
        );
    }

    if(bookinfo.has(timeline_event.googlebookid)) {
        bookdescription = bookinfo.get(timeline_event.googlebookid).description;
        author = bookinfo.get(timeline_event.googlebookid).author;
        thumbnail =  bookinfo.get(timeline_event.googlebookid).thumbnail;
    }


    if(timeline_event.action === "Progress") {

        return (<TimelineProgressCard username={timeline_event.name}
                                  bookname={timeline_event.bookName}
                                  comment={timeline_event.content}
                                  author={author}
                                  bookdescription={bookdescription}
                                  progress={timeline_event.progress}
                                  time={timeline_event.time}
                                  image={thumbnail}
                                  id={timeline_event.id}
        />);
    }
    else {  // for "Review" "Reading" "favorite"

        return (<RecipeReviewCard username={timeline_event.name}
                                  bookname={timeline_event.bookName}
                                  comment={timeline_event.content}
                                  author={author}
                                  action={timeline_event.action}
                                  bookdescription={bookdescription}
                                  rate={timeline_event.rating}
                                  time={timeline_event.time}
                                  image={thumbnail}
                                  id={timeline_event.id}
                                  googleBookId={timeline_event.googlebookid}
        />);
    }

}
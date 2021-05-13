import React, {useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Collapse from '@material-ui/core/Collapse';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import {blue, red} from '@material-ui/core/colors';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import {Rating} from "@material-ui/lab";
import Box from "@material-ui/core/Box";
import LinearWithValueLabel from "./ReadingProgress";
import {Input} from "@material-ui/core";
import SendIcon from '@material-ui/icons/Send';
import Button from "@material-ui/core/Button";
import {Send} from "@material-ui/icons";
import axios from "axios";
import ReplyCard from "./ReplyCard";
import parse from 'html-react-parser';
import Link from "@material-ui/core/Link";
import {sendMessage, socket} from "./Socketio";
import Divider from "@material-ui/core/Divider";

const currentUser = {id: sessionStorage.getItem('currentUserID'), name: sessionStorage.getItem('currentUser')}

const useStyles = makeStyles((theme) => ({
    root: {
        maxWidth: "900px",
        maxHeight:"20%",
        margin: "20px auto",
        boxShadow: "0px 10px 11px 2px #ccc",
        borderRadius:"20px",


    },
    media: {
        display: "inline-block",
        height: "100%",
        width: "30%",
        textAlign:"center",

    },
    bookContainer: {
        height:"50%",
    },
    bookImg: {
        display: "inline-block",
        height:"90%",
        marginLeft: "10px",
        maxWidth: "100%",
        boxShadow: "3px 3px 5px 6px #ccc",

    },
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    },
    expandOpen: {
        transform: 'rotate(180deg)',
    },
    avatar: {
        backgroundColor: blue[500],
    },
    bookInfo: {
        verticalAlign: "top",
        marginLeft: "1px",
        marginTop: "0px",
        display: "inline-block",
        width: '63%',
        height: '170px',
        overflow:'auto',
        // borderWidth: "1px",
        // borderStyle:"solid",
        // borderColor:"lightgray",
        // backgroundColor:"aliceblue",
    },
    replyField: {
        width:"100%",
        borderRadius:"20px",
    },
    replyTxt: {
        width:"80%",
    },
    replyBtn: {
        marginLeft: "10px",
        float:"right",
    },
    slider: {
        marginTop: 40,
        width: 400,
    },
    cardContent: {
        height: "60px",
        padding: "0 0 0 2%",
    },
    reviewContent: {
        width:"100%",
        overflow:"auto",
        marginLeft: "3em",
    },
    divider: {
        margin: "20px",
        height: "2px",
    },
    startIcon: {
        marginLeft:"1.9em",
    }
}));

export default function RecipeReviewCard(props) {
    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);
    const [input_txt, setInputTxt] = React.useState("");
    const [userreply, setUserReply] = React.useState(null);
    const [updatePage, setUpdatePage] = React.useState(false);
    const userid = sessionStorage.getItem('currentUserID');
    let statement = "";
    let reviewComment = "";
    let ratingComp = "";

    if(props.action === "Reading") {
        statement = "starts reading";
        reviewComment = <Typography className={classes.reviewContent} variant="body1" gutterBottom>
            {props.comment}
        </Typography>;
    }
    else if(props.action === "Favorite") {
        statement = "moves a book";
        reviewComment = <Typography className={classes.reviewContent} variant="body1" gutterBottom>
            {props.comment}
        </Typography>;
    }
    else if(props.action === "Review") {
        statement = "posts a review:";

        reviewComment = <Typography className={classes.reviewContent} variant="body1" gutterBottom>
                            {props.comment}
                        </Typography>;
        ratingComp = <Rating className={classes.startIcon} name="read-only" value={props.rate} precision={0.5} readOnly />;

    }

    const fetchReply = (eventid) => {

        axios.get(`/api/reply?eventid=${eventid}`)
            .then(res =>{
                if(res.data === userreply)
                    return;
                setUserReply(res.data);
            })
            .catch( error => {
            });
    }

    // send real time event to socketio
    const sendEventToSocket = (eventName) => {

        axios.get(`/api/relationship/friends/${userid}`)
            .then(res =>{

            let userids = userid;
            res.data.map((item) => {
                userids += "," + item.id;
            });
            sendMessage( eventName, userids);
        })
        .catch( error => {
        });
    }

    // for socket.io subsribe the a new topic to get realtime reply
    useEffect(() =>{

        socket.on("refreshReply", (res) => {
            setUpdatePage(true);
        });
        socket.on("updateTimelinePage", (res) => {
            setUpdatePage(true);
        });
    }, []);

    // refresh new reply when receive updatePage event from socket.io
    useEffect(() =>{
        if(updatePage) {
            fetchReply(props.id);
            setUpdatePage(false);
        }
    }, [updatePage]);



    useEffect(() =>{
        fetchReply(props.id);
    }, []);

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const convertTime = () => {

        const date = new Date(props.time);
        return date.toDateString() + "  " + date.getHours() + ":" + date.getMinutes() +  ":" +  date.getSeconds();
    }

    const getTxt = (event) => {

        const { value } = event.target;
        setInputTxt(value);
    }

    const inputKeyDown = (event) => {

        if(event.key === 'Enter')
            sendReply();
    }

    const sendReply = () => {

        if(input_txt === "") return;

        /* Reply the comment */
        axios({
            method: 'post',
            url: '/api/reply',
            data: {
                eventid:props.id,
                bookname: props.bookname,
                reply: input_txt,
                receiver: props.username,
                sender: currentUser.name,
            }
        }).then(() => {
            sendEventToSocket("newReply");
            // ftechReply(props.id);
        });
        setInputTxt("");
    } // user reply

    return (

        <Card className={classes.root} variant="outlined">
            <CardHeader
                avatar={
                    <Avatar aria-label="recipe" className={classes.avatar}>
                        {props.username.charAt(0).toUpperCase()}
                    </Avatar>
                }
                title={props.username}
                subheader={convertTime()}
            />
            <div className={classes.bookContainer}>
              <Link href={"/book/" + props.googleBookId} color="inherit">

                <CardMedia className={classes.media} title={props.title}>
                  
                    <img className={classes.bookImg} src={props.image}/>

                </CardMedia>
              </Link>
                <div className={classes.bookInfo}>
                  <Link href={"/book/" + props.googleBookId} color="inherit">

                    <Typography variant="h6" gutterBottom>
                        {props.bookname}
                    </Typography>
                  </Link>
                    <Typography variant="h6" color="secondary" gutterBottom>
                        {props.author}
                    </Typography>

                    <Typography variant="body1" gutterBottom>
                        {parse(props.bookdescription)}
                    </Typography>
                </div>
            </div>
            <Divider className={classes.divider}/>
            <CardContent className={classes.cardContent}>
                <Typography variant="h6" color="textSecondary" gutterBottom> 
                    {`${props.username} ${statement}`}
                </Typography>
                {reviewComment}
                {ratingComp}
            </CardContent>
            <CardActions disableSpacing>
                <IconButton
                    className={clsx(classes.expand, {
                        [classes.expandOpen]: expanded,
                    })}
                    onClick={handleExpandClick}
                    aria-expanded={expanded}
                    aria-label="show more"
                >
                    <ExpandMoreIcon/>
                </IconButton>
            </CardActions>
            <Collapse in={expanded} timeout="auto" unmountOnExit>
                <CardContent>
                    <Typography variant="h6" gutterBottom> Comment </Typography>
                    {userreply && userreply.map((r, idx) => {

                        return (
                            <ReplyCard
                                bookName={r.bookname}
                                eventId={r.eventid}
                                receiver={r.receiver}
                                reply={r.reply}
                                sender={r.sender}
                                time={convertTime(r.time)}
                                key={idx}
                            />
                        );
                    })}
                    <div className={classes.replyField}>
                        <Input className={classes.replyTxt} placeholder="Write Comment.." inputProps={{ 'aria-label': 'description' }} value={input_txt} onChange={getTxt} onKeyDown={inputKeyDown}/>
                        <Button className={[classes.button, classes.replyBtn]} variant="contained"  color="primary" onClick={sendReply}>
                            Reply
                            <SendIcon/>
                        </Button>
                    </div>
                </CardContent>
            </Collapse>
        </Card>
    );
}
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
import Slider from "@material-ui/core/Slider";
import Grid from "@material-ui/core/Grid";

const currentUser = {id: sessionStorage.getItem('currentUserID'), name: sessionStorage.getItem('currentUser')}

const useStyles = makeStyles((theme) => ({
    root: {
        maxWidth: "900px",
        maxHeight:"20%",
        margin: "20px auto",
        boxShadow: "0px 10px 11px 2px #ccc",


    },
    media: {
        display: "inline-block",
        height: "30vh",
        width: "30%",
        textAlign:"center",

    },
    bookContainer: {
        height:"70%",
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
        marginLeft: "4vw",
        marginTop: "0px",
        display: "inline-block",
        width: '60%',
        height: '250px',
        overflow:'auto',

    },
    replyField: {
        width:"100%",
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
    },
    reviewContent: {
        height: "100%",
        width:"100%",
        overflow:"auto",
    }
}));

export default function RecipeReviewCard(props) {
    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);
    const [input_txt, setInputTxt] = React.useState("");
    const [userreply, setUserReply] = React.useState(null);


    const ftechReply = (eventid) => {

        axios.get(`/api/reply?eventid=${eventid}`)
            .then(res =>{
                if(res.data === userreply)
                    return;
                // console.log(res);
                setUserReply(res.data);

            })
            .catch( error => {
            });
    }

    // setInterval(ftechReply, 5000);


    useEffect(() =>{
        ftechReply(props.id);
    }, []);

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const convertTime = () => {

        const date = new Date(props.time);
        return date.toDateString() + "  " + date.getHours() + ":" + date.getMinutes() +  ":" +  date.getSeconds();
    }

    const actionType = (action) => {  // TODO

        if(action === "review")
            return "User wrote a review";
        else
            return "User did an action";
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
            ftechReply(props.id);
        });
        setInputTxt("");
    } // user reply

    return (
        <Card className={classes.root}>
            <CardHeader
                avatar={
                    <Avatar aria-label="recipe" className={classes.avatar}>
                        {currentUser.name.charAt(0).toUpperCase()}
                    </Avatar>
                }
                title={props.username}
                subheader={convertTime()}
            />
            <div>
                <CardMedia className={classes.media} title={props.title}>
                    <img className={classes.bookImg} src={props.image}/>
                    <LinearWithValueLabel value={props.progress}></LinearWithValueLabel>
                    <Box component="fieldset" mb={3} borderColor="transparent">
                        <Rating name="read-only" value={props.rate} precision={0.5} readOnly />
                    </Box>
                </CardMedia>
                <div className={classes.bookInfo}>
                    <Typography variant="h6" gutterBottom>
                        {props.bookname}
                    </Typography>
                    <Typography variant="h6" color="secondary" gutterBottom>
                        {props.author}
                    </Typography>

                    <Typography variant="body1" gutterBottom>
                        {parse(props.bookdescription)}
                    </Typography>
                </div>
            </div>
            <CardContent className={classes.cardContent}>
                <Typography variant="h6" gutterBottom> Review </Typography>
                <Typography className={classes.reviewContent} variant="body1" color={"textSecondary"} gutterBottom>
                    {props.comment}
                </Typography>
            </CardContent>
            <CardActions disableSpacing>
                {/*<IconButton aria-label="share">*/}
                {/*    <ShareIcon/>*/}
                {/*</IconButton>*/}
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
                        // console.log(r);
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
                        <Input className={classes.replyTxt} placeholder="Write Comment..." inputProps={{ 'aria-label': 'description' }} value={input_txt} onChange={getTxt} onKeyDown={inputKeyDown}/>
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
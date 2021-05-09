import React from 'react';
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

const currentUser = {id: sessionStorage.getItem('currentUserID'), name: sessionStorage.getItem('currentUser')}

const useStyles = makeStyles((theme) => ({
    root: {
        maxWidth: "900px",
        maxHeight:"20%",
        margin: "20px auto",

    },
    media: {
        height: "30vh",
        width: "100%",

    },
    bookImg: {
        display: "inline-block",
        marginLeft: "5%",
        maxHeight:"100%",
        maxWidth: "100%",
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
    bookName: {
        verticalAlign: "top",
        marginLeft: "4vw",
        marginTop: "0px",
        display: "inline-block",
        color: "#1274f1",
        width: 'calc(100% - 300px)',
        height: '100%',

    },
    replyField: {
        width:"100%",
    },
    replyTxt: {
        width:"80%",
    },
    replyBtn: {
        marginLeft: "10px",
    },
}));

export default function RecipeReviewCard(props) {
    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);
    const [input_txt, setInputTxt] = React.useState("");

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
            console.log("props.bookname : ", props.id);
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
            <CardMedia className={classes.media} title={props.title}>
                <img className={classes.bookImg} src={props.image}/>
                <div className={classes.bookName}>
                    <Typography variant="h5" color="primary" component="h5">
                        Book Name:  {props.bookname}
                    </Typography>
                </div>
            </CardMedia>
            <LinearWithValueLabel variant="determinate" value={props.progress}/>
            <CardContent>
                <Typography variant="subtitle1" color="textSecondary" component="p">
                    <div>Review:</div>
                    {props.comment}
                </Typography>
            </CardContent>
            <CardActions disableSpacing>
                <Box component="fieldset" mb={3} borderColor="transparent">
                    <Rating name="half-rating-read" defaultValue={2.5} precision={0.5} readOnly />
                </Box>
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
                    <Typography paragraph>Review:</Typography>
                    <Typography paragraph>
                    </Typography>
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
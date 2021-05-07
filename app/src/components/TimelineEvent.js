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

const useStyles = makeStyles((theme) => ({
    root: {
        maxWidth: "60%",
        maxHeight:"20%",
        margin: "20px auto",

    },
    media: {
        height: "40vh",
        // paddingTop: '56.25%', // 16:9
        width: "100%",
        textAlign: "center",
    },
    bookImg: {
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
        margin: "10px 0 0 auto",
        color: "#203F67",
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

    const sendReply = () => { // TODO

        if(input_txt === "") return;

        console.log(input_txt);
        setInputTxt("");
    }

    return (
        <Card className={classes.root}>
            <CardHeader
                avatar={
                    <Avatar aria-label="recipe" className={classes.avatar}>
                        P
                    </Avatar>
                }
                title={props.username}
                subheader={props.time}
            />
            <CardMedia className={classes.media} title={props.title}>
                <img className={classes.bookImg} src={props.image}/>
                <LinearWithValueLabel variant="determinate" value={props.progress}/>
            </CardMedia>

            <CardContent>
                <Typography className={classes.bookName} variant="h5" color="primary" component="h5">
                    Book Name:  {props.bookname}
                </Typography>
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
                    <Typography paragraph> // users' comments
                        fgdg
                        dfgfdg
                        dfgdfg
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
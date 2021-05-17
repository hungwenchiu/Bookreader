// Card component in friendship page
import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles({
    root: {
        maxWidth: "900px",
        maxHeight: "20%",
        margin: "20px auto",
        boxShadow: "0px 10px 11px 2px #ccc",
        textAlign: "center",
        borderRadius: "20px",
    },
    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    },
    CardContent: {
        padding: "9px 0 9px 0",
        paddingBottom: "9px",
    }
});


export default function FriendShipEventCard(props) {
    const classes = useStyles();

    const convertTime = () => {

        const date = new Date(props.time);
        return date.toDateString() + "  " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    }


    return (
        <Card className={classes.root} variant="outlined">
            <CardContent className={classes.CardContent}>
                <Typography variant="h6" component="h2">
                    {props.content}
                </Typography>
                <Typography className={classes.title} color="textSecondary" gutterBottom>
                    {convertTime()}
                </Typography>
            </CardContent>
        </Card>
    );
}
import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles({
    root: {
        maxWidth: "900px",
        maxHeight:"20%",
        margin: "20px auto",
        boxShadow: "0px 10px 11px 2px #ccc",
        textAlign:"center",
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
});


export default function FriendShipEventCard(props) {
    const classes = useStyles();

    const convertTime = () => {

        const date = new Date(props.time);
        return date.toDateString() + "  " + date.getHours() + ":" + date.getMinutes() +  ":" +  date.getSeconds();
    }


    return (
        <Card className={classes.root} variant="outlined">
            <CardContent>
                <Typography variant="h5" component="h2">
                    {props.content}
                </Typography>
                <Typography className={classes.title} color="textSecondary" gutterBottom>
                    {convertTime()}
                </Typography>
            </CardContent>
        </Card>
    );
}
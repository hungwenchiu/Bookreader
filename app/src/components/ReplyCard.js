import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles({
    root: {
        minWidth: 275,
        margin: "10px 0 10px 0",
        overflow: "auto",
        borderRadius:"10px",
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

export default function ReplyCard(props) {
    const classes = useStyles();
    const bull = <span className={classes.bullet}>•</span>;

    return (
        <Card className={classes.root} variant="outlined">
            <CardContent>
                <Typography className={classes.title} color="textSecondary" style={{color:""}} gutterBottom>
                    {props.sender} says:
                </Typography>
                <Typography variant="body2" component="p">
                    {props.reply}
                </Typography>
                <Typography className={classes.title} color="textSecondary" gutterBottom>
                    {props.time}
                </Typography>
            </CardContent>
        </Card>
    );
}
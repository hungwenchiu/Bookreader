// reply component in timeline
import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles({
  root: {
    minWidth: 275,
    margin: "10px 0 10px 0",
    overflow: "auto",
    borderRadius: "10px",
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
  replyContext: {
    marginLeft: "1.5em",
  },
});

export default function ReplyCard(props) {
  const classes = useStyles();

  return (
    <Card className={classes.root} variant="outlined">
      <CardContent>
        <Typography className={classes.title} color="textSecondary" style={{ color: "" }} gutterBottom>
          {props.sender} says:
        </Typography>
        <Typography className={classes.replyContext} variant="body1">
          {props.reply}
        </Typography>
        <Typography className={[classes.replyContext, classes.title]} color="textSecondary">
          {props.time}
        </Typography>
      </CardContent>
    </Card>
  );
}
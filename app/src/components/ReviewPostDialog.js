import React, { useEffect } from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Rating from '@material-ui/lab/Rating';
import Alert from '@material-ui/lab/Alert';
import IconButton from '@material-ui/core/IconButton';
import Collapse from '@material-ui/core/Collapse';
import CloseIcon from '@material-ui/icons/Close';
import axios from "axios";
import { initiateSocket, sendMessage } from "./Socketio";

export default function ReviewPostDialog(props) {

  const username = sessionStorage.getItem("currentUser");
  const userid = sessionStorage.getItem("currentUserID");

  const { bookInfo, allReviews, setReviews } = props;
  const [open, setOpen] = React.useState(false);
  const [alertOpen, setAlertOpen] = React.useState(false);
  const [inputtxt, setInputTxt] = React.useState("");
  const [newRating, setNewRating] = React.useState(0);

  // for socket io connection and set subcriptions
  useEffect(() => {
    initiateSocket(userid);
  }, []);

  // send real time event to socketio
  const sendEventToSocket = (eventName) => {

    axios.get(`/api/relationship/friends/${userid}`)
      .then(res => {

        let userids = userid;
        res.data.map((item) => {
          userids += "," + item.id;
        });
        sendMessage(eventName, userids);
      })
      .catch(error => {
      });
  }

  // get value of input text
  const getTxt = (event) => {
    const { value } = event.target;
    setInputTxt(value);
  }

  // call API and insert to event table
  const postEvent = () => {
    if (newRating === 0 || inputtxt === "") {
      setAlertOpen(true);
    } else {

      axios.post(`/api/event`, {

            userid: userid,
            name: username,
            bookName: bookInfo.volumeInfo.title,
            action: "Review",
            content: inputtxt,
            googlebookid: bookInfo.id,
            rating: newRating,
            progress: null,
          }
      )
        .then(res => {
          console.log("post event success");
          sendEventToSocket("newPost");
          handleClose();
        });

      axios.post(`/api/review`, {
        googleBookId: bookInfo.id,
        userId: userid,
        content: inputtxt,
        rating: newRating,
      })
        .then(res => {
          console.log("post review success");
          handleClose();

          setReviews(allReviews.concat(res.data))
        });

    }
  }

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setAlertOpen(false);
    setNewRating(0);
  };

  return (
    <div>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        Post Review
      </Button>
      <Dialog maxWidth={'lg'} open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Review</DialogTitle>
        <Collapse in={alertOpen}>
          <Alert
            severity="error"
            action={
              <IconButton
                color="inherit"
                size="small"
                onClick={() => {
                  setAlertOpen(false);
                }}
              >
                <CloseIcon fontSize="inherit" />
              </IconButton>
            }
          >
            Rating or review is empty!
          </Alert>
        </Collapse>
        <DialogContent>
          <Rating
            value={newRating}
            precision={0.5}
            onChange={(event, newValue) => {
              setNewRating(newValue)
            }}
          />

          <DialogContentText>
            Please write down your review
          </DialogContentText>
          <TextField style={{ width: "30vw" }}
            id="outlined-multiline-static"
            multiline
            rows={4}
            variant="outlined"
            autoFocus={true}
            size={'medium'}
            onChange={getTxt}
          />

        </DialogContent>
        <DialogActions>
          <Button onClick={postEvent} color="primary">
            Post
          </Button>
          <Button onClick={handleClose} color="secondary">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
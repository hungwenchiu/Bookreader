import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Rating from '@material-ui/lab/Rating';
import axios from "axios";

export default function ReviewPostDialog(props) {

  const username = sessionStorage.getItem("currentUser");
  const userid = sessionStorage.getItem("currentUserID");

  const { bookInfo } = props;
  const [open, setOpen] = React.useState(false);
  const [inputtxt, setInputTxt] = React.useState("");
  const [newRating, setNewRating] = React.useState(0);

  // get value of input text
  const getTxt = (event) => {
    const { value } = event.target;
    setInputTxt(value);
  }

  // call API and insert to event table
  const postEvent = () => {

    const params = new URLSearchParams();
    params.append("userid", userid);
    params.append("name", username);
    params.append("bookName", bookInfo.volumeInfo.title);
    params.append("action", "Review");
    params.append("content", inputtxt);
    params.append("googlebookid", bookInfo.id);
    params.append("rating", newRating); // TODO - input the rating of the book

    axios.post(`/api/event`, params
    )
      .then(res => {
        console.log("post event success");
        handleClose();
      });
  }

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        Post Review
      </Button>
      <Dialog maxWidth={'lg'} open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Review</DialogTitle>

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
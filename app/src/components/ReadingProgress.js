import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import LinearProgress from '@material-ui/core/LinearProgress';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import {withStyles} from "@material-ui/core";

function LinearProgressWithLabel(props) {
    const classes = useStyles();
    return (
        <Box display="flex" alignItems="center">
            <Box width="100%" mr={1}>
                <LinearProgress className={classes.progressBar} color="primary" variant="determinate" {...props}/>
            </Box>
            <Box minWidth={35}>
                <Typography variant="body2" color="textSecondary">{`${Math.round(
                    props.value,
                )}%`}</Typography>
            </Box>
        </Box>
    );
}

const BorderLinearProgress = withStyles((theme) => ({
    root: {
        display:"block",
        marginTop: "10px",
        marginBottom: "10px",
        margin: "0 0",
        width: "100%",
        height: "20px",
        borderRadius: 5,
        boxShadow: "0 5px 5px 0 #ccc",

    },
    colorPrimary: {
        backgroundColor: theme.palette.grey[theme.palette.type === 'light' ? 200 : 700],
    },
    bar: {
        borderRadius: 5,
        backgroundColor: '#7ECB3C',
            // '#1a90ff',
    },
}))(LinearProgress);

LinearProgressWithLabel.propTypes = {
    /**
     * The value of the progress indicator for the determinate and buffer variants.
     * Value between 0 and 100.
     */
    value: PropTypes.number.isRequired,
};

const useStyles = makeStyles({
    root: {
        width: '100%',
    },
    progressBar: {
        height: "6px",
    },
    scoreTxt: {

    },
});

export default function LinearWithValueLabel(props) {
    const classes = useStyles();
    const [progress, setProgress] = React.useState(0);
    const {value} = props;
    const target = (isNaN(value)) ? 100 : value;

    React.useEffect(() => {

        if(progress >= target)
            return;

        const timer = setInterval(() => {
            setProgress(progress + 1);
        }, 20);
        return () => {
            clearInterval(timer);
        };
    }, [progress]);

    return (
        <div className={classes.root}>
            {/*<LinearProgressWithLabel value={progress} />*/}
            <BorderLinearProgress color="primary" variant="determinate" value={progress} />
        </div>
    );
}
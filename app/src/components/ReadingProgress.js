import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import LinearProgress from '@material-ui/core/LinearProgress';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';

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
    const target = (isNaN(props.progress)) ? 100 : props.progress;

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
            <LinearProgressWithLabel value={progress} />
        </div>
    );
}
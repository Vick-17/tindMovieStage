import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import FavoriteIcon from '@mui/icons-material/Favorite';
import CancelOutlinedIcon from '@mui/icons-material/CancelOutlined';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import StarIcon from "@mui/icons-material/Star";
import { swipeLike } from "../../service/apiService";

const CardList = ({ title, subheader, image, content, actor, movieId, userId, onRemove }) => {

    const ExpandMore = styled((props) => {
        const { expand, ...other } = props;
        return <IconButton {...other} />;
    })(({ theme, expand }) => ({
        transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    }));

    const [expanded, setExpanded] = useState(false);

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const handleStarIconClick = () => {
        if (movieId) {
            window.location.href = `/commentaire/${movieId}`;
        }
    };


    const like = async (swipeDirection) => {
        if (userId) {
            const likeData = {
                userId: userId,
                filmId: movieId,
                swipeDirection: swipeDirection
            };
            setShouldRemove(true);
            await swipeLike(likeData);
        } else {
            window.location.href = `/login`;
        }
    };

    // Utilisez une variable d'état locale pour contrôler si le film doit être supprimé
    const [shouldRemove, setShouldRemove] = useState(false);

    // Si shouldRemove devient true, appelez onRemove(movieId) et réinitialisez l'état local
    useEffect(() => {
        if (shouldRemove) {
            onRemove(movieId);
            setShouldRemove(false);
        }
    }, [shouldRemove, movieId, onRemove]);

    return (
        <Card className='all_movie_card' >
            <CardHeader
                title={title}
                subheader={subheader}
            />
            <CardMedia
                component="img"
                height="194"
                image={image}
                alt="Paella dish"
            />
            <CardContent>
                <Typography variant="body2" color="text.secondary">
                    {actor}
                </Typography>
            </CardContent>
            <CardActions disableSpacing>
                <IconButton onClick={() => like('right')}>
                    <FavoriteIcon />
                </IconButton>
                <IconButton onClick={() => like('left')}>
                    <CancelOutlinedIcon />
                </IconButton>
                <IconButton onClick={handleStarIconClick}>
                    <StarIcon
                        style={{ color: "#FFAC33" }}
                    />
                </IconButton>
                <ExpandMore
                    expand={expanded}
                    onClick={handleExpandClick}
                    aria-expanded={expanded}
                    aria-label="show more"
                >
                    <ExpandMoreIcon />
                </ExpandMore>
            </CardActions>
            <Collapse in={expanded} timeout="auto" unmountOnExit>
                <CardContent>
                    <Typography paragraph>Synopsis:</Typography>
                    <Typography paragraph>
                        {content}
                    </Typography>
                    <Typography>
                        Set aside off of the heat to let rest for 10 minutes, and then serve.
                    </Typography>
                </CardContent>
            </Collapse>
        </Card>
    );
};

export default CardList;

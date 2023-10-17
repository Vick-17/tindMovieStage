import React from 'react';

const Comment = ({content, author} ) => {
    return (
        <div className="commentaire">
            <p>{content}</p>
            <div className="author_container">
                <h6>- {author}</h6>
            </div>
        </div>
    );
};

export default Comment;
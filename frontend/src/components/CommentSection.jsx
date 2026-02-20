import { useState } from 'react';
import api from '../services/api';
import { useAuth } from '../context/AuthContext';

export default function CommentSection({ musicId, comments = [], onCommentAdded }) {
  const [newComment, setNewComment] = useState('');
  const [error, setError] = useState('');
  const { user } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!newComment.trim()) return;

    try {
      await api.post(`/api/musics/comments/${musicId}/${user.id}`, {
        comments: newComment,
      });
      setNewComment('');
      setError('');
      if (onCommentAdded) onCommentAdded();
    } catch (err) {
      setError('Failed to post comment');
    }
  };

  return (
    <div className="comment-section">
      <h4>Comments ({comments.length})</h4>

      {user && (
        <form onSubmit={handleSubmit} className="comment-form">
          <textarea
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            placeholder="Add a comment..."
            maxLength={500}
          />
          <button type="submit">Post Comment</button>
          {error && <p className="error">{error}</p>}
        </form>
      )}

      <div className="comments-list">
        {comments.length === 0 ? (
          <p>No comments yet</p>
        ) : (
          comments.map((comment, index) => (
            <div key={index} className="comment">
              <p>{comment.comments}</p>
              <small>User #{comment.userId}</small>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

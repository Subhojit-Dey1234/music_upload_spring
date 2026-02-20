import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import api from '../services/api';
import MusicPlayer from '../components/MusicPlayer';

export default function Profile() {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await api.get(`/api/users/${id}`);
        setUser(response.data);
      } catch (err) {
        setError('Failed to load profile');
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, [id]);

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;
  if (!user) return <div className="error">User not found</div>;

  return (
    <div className="profile-page">
      <h1>{user.name}</h1>
      <p className="email">{user.email}</p>

      <section className="profile-section">
        <h2>Liked Music ({user.musicLiked?.length || 0})</h2>
        {user.musicLiked?.length > 0 ? (
          <div className="liked-list">
            {user.musicLiked.map((music) => (
              <div key={music.id} className="liked-item">
                <MusicPlayer musicId={music.id} musicName={music.name} />
              </div>
            ))}
          </div>
        ) : (
          <p>No liked music yet</p>
        )}
      </section>

      <section className="profile-section">
        <h2>Comments ({user.commentedPosts?.length || 0})</h2>
        {user.commentedPosts?.length > 0 ? (
          <div className="comments-list">
            {user.commentedPosts.map((comment, index) => (
              <div key={index} className="comment-item">
                <p>{comment.comments}</p>
                <small>on Music #{comment.musicId}</small>
              </div>
            ))}
          </div>
        ) : (
          <p>No comments yet</p>
        )}
      </section>

      <Link to="/" className="back-link">Back to Home</Link>
    </div>
  );
}

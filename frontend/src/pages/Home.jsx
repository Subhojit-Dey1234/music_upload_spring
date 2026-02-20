import { useState, useEffect } from 'react';
import api from '../services/api';
import MusicCard from '../components/MusicCard';
import CommentSection from '../components/CommentSection';

export default function Home() {
  const [musics, setMusics] = useState([]);
  const [selectedMusic, setSelectedMusic] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchMusics = async () => {
    try {
      const response = await api.get('/api/musics/all');
      setMusics(response.data);
    } catch (err) {
      setError('Failed to load music');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMusics();
  }, []);

  const handleSelect = (music) => {
    setSelectedMusic(selectedMusic?.id === music.id ? null : music);
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="home">
      <h1>All Music</h1>

      {musics.length === 0 ? (
        <p>No music uploaded yet</p>
      ) : (
        <div className="music-grid">
          {musics.map((music) => (
            <div key={music.id}>
              <MusicCard music={music} onSelect={handleSelect} />
              {selectedMusic?.id === music.id && (
                <CommentSection
                  musicId={music.id}
                  comments={music.comments || []}
                  onCommentAdded={fetchMusics}
                />
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

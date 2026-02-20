import { Link } from 'react-router-dom';
import MusicPlayer from './MusicPlayer';

export default function MusicCard({ music, onSelect }) {
  return (
    <div className="music-card">
      <div className="music-info">
        <h3>{music.name}</h3>
        <p>
          by <Link to={`/profile/${music.userId}`}>User #{music.userId}</Link>
        </p>
        <p>{music.numberOfLikes} likes</p>
      </div>
      <MusicPlayer musicId={music.id} musicName={music.name} />
      <button onClick={() => onSelect(music)} className="details-btn">
        View Details
      </button>
    </div>
  );
}

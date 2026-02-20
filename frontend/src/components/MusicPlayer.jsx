import { useRef, useState } from 'react';

export default function MusicPlayer({ musicId, musicName }) {
  const audioRef = useRef(null);
  const [isPlaying, setIsPlaying] = useState(false);

  const togglePlay = () => {
    if (isPlaying) {
      audioRef.current.pause();
    } else {
      audioRef.current.play();
    }
    setIsPlaying(!isPlaying);
  };

  return (
    <div className="music-player">
      <audio
        ref={audioRef}
        src={`http://localhost:8080/api/musics/stream/${musicId}`}
        onEnded={() => setIsPlaying(false)}
      />
      <button onClick={togglePlay} className="play-btn">
        {isPlaying ? '⏸ Pause' : '▶ Play'}
      </button>
      <span className="music-name">{musicName}</span>
    </div>
  );
}

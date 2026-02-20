import { useState } from 'react';
import api from '../services/api';
import { useAuth } from '../context/AuthContext';

export default function UploadForm({ onSuccess }) {
  const [name, setName] = useState('');
  const [file, setFile] = useState(null);
  const [error, setError] = useState('');
  const [uploading, setUploading] = useState(false);
  const { user } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file || !name.trim()) {
      setError('Please provide a name and select a file');
      return;
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('name', name);
    formData.append('userId', user.id);

    setUploading(true);
    setError('');

    try {
      await api.post('/api/musics/', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      setName('');
      setFile(null);
      if (onSuccess) onSuccess();
    } catch (err) {
      setError(err.response?.data?.message || 'Upload failed');
    } finally {
      setUploading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="upload-form">
      <div className="form-group">
        <label>Track Name</label>
        <input
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Enter track name"
        />
      </div>

      <div className="form-group">
        <label>Music File (MP3 only)</label>
        <input
          type="file"
          accept=".mp3,audio/mpeg"
          onChange={(e) => setFile(e.target.files[0])}
        />
      </div>

      {error && <p className="error">{error}</p>}

      <button type="submit" disabled={uploading}>
        {uploading ? 'Uploading...' : 'Upload'}
      </button>
    </form>
  );
}

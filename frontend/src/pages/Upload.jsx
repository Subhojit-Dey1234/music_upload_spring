import { useNavigate } from 'react-router-dom';
import UploadForm from '../components/UploadForm';

export default function Upload() {
  const navigate = useNavigate();

  const handleSuccess = () => {
    navigate('/');
  };

  return (
    <div className="upload-page">
      <h1>Upload Music</h1>
      <p>Share your music with the world. Only MP3 files are supported.</p>
      <UploadForm onSuccess={handleSuccess} />
    </div>
  );
}

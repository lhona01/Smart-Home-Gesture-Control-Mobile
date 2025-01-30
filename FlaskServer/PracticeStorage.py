from flask import Flask, request, jsonify
import os

def handleDuplicateFileName(file_name, upload_folder):
    count = 1
    base_name, extension = os.path.splitext(file_name)
    new_file_name = file_name

    while os.path.exists(os.path.join(upload_folder, new_file_name)):
        new_file_name = f"{base_name}_{count}{extension}"
        count += 1
    
    return new_file_name

app = Flask(__name__)
UPLOAD_FOLDER = './Recordings'
os.makedirs(UPLOAD_FOLDER, exist_ok=True)  # Ensure folder exists
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'video' not in request.files:  # Changed 'file' to 'video'
        return jsonify({"error": "No file part"}), 400
    file = request.files['video']
    if file.filename == '': # File name check
        return jsonify({"error": "No selected file"}), 400
    if file.filename.endswith('.mp4'): # File Type check
        file.filename = handleDuplicateFileName(file.filename, app.config['UPLOAD_FOLDER'])
        file_path = os.path.join(app.config['UPLOAD_FOLDER'], file.filename)
        file.save(file_path)
        return jsonify({"message": "File uploaded successfully", "path": file_path}), 200
    else:
        return jsonify({"error": "Invalid file type"}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)  # Allow access from other devices

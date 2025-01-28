from flask import Flask, request, jsonify
import os

app = Flask(__name__)
UPLOAD_FOLDER = './Recordings'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return jsonify({"error": "No file part"}), 400
    file = request.files['file']
    if file.filename == '':
        return jsonify({"error": "No selected file"}), 400
    if file and file.filename.endswith('.mp4'):
        file_path = os.path.join(app.config['UPLOAD_FOLDER'], file.filename)
        file.save(file_path)
        return jsonify({"message": "File uploaded successfully"}), 200
    else:
        return jsonify({"error": "Invalid file type"}), 400

if __name__ == '__main__':
    app.run(debug=True)
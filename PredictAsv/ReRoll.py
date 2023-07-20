# -*- coding: utf-8 -*-

from flask import Flask, request, jsonify
from flask_restx import Api, Resource
from keras.utils import pad_sequences
from keras.models import load_model
import numpy as np
import json
import pickle as pk
import re
import codecs
import mariadb
import os
import glob
from natsort import natsorted
from tensorflow.keras.models import load_model

#받은 text를 리스트에 담아야 model이 돌아갑니다
text_read = []

#모델불러오기
with open("./model/tokenizer_pre.pickle", "rb") as f:
    tokenizer1 = pk.load(f)
# model1 = load_model("./model/model_pre.h5")

folder_path = './model'
base_file_name = 'modelVer'
# 모든 모델 파일 가져오기
model_files = glob.glob(os.path.join(folder_path, f'{base_file_name}_*.h5'))
if model_files:
    # 파일 이름을 기준으로 정렬하여 가장 최신 파일 찾기
    sorted_model_files = natsorted(model_files)
    latest_model_file = sorted_model_files[-1]
    model = load_model(latest_model_file)
else:
    # 모델 파일이 없는 경우 처리할 내용 작성
    print("No model file found.")
    model = None

#재학습
def predict(string):
    real_sequences1 = tokenizer1.texts_to_sequences([string])
    real_seq1 = pad_sequences(real_sequences1, maxlen=1000, truncating="pre")
    result1 = model1.predict(real_seq1)
#재학습한 STT 판단결과
    return result1


app = Flask(__name__)
api = Api(app)


@api.route("/api/text/<string:user_id>/<string:declaration>", methods=["POST"])
class HelloWorld(Resource):
    def post(self, user_id, declaration):
        global text_read
        declaration = re.sub("[^0-9]", "", declaration)
        print(declaration)
        text = request.form.get('text')
        print(text)
        decoded_text = codecs.decode(text.encode(), 'utf-8')
        print(decoded_text)
        text_read.append(decoded_text)
        print(text_read)
        prediction = predict(text_read)
        prediction = prediction.tolist()
        print(prediction)
        #반환 할 데이터
        data = {
            'user_id': user_id,
            'phone': declaration,
            'result': prediction
        }        
        print(data)
        #DB 연결
        conn = mariadb.connect(
            user="root",
            password="hkit301301",
            host="182.229.34.184",
            port=3306,
            database="Testplace_02",
        )
        print("DB C")
        # 수정 통화테이블의 리롤칼럼
        cursor = conn.cursor()
        print("1")
        query = f"""UPDATE voicedata SET reroll='{prediction}',modified_date=NOW() where user_id='{user_id}' and declaration='{declaration}'"""
        print("2")
        cursor.execute(query)
        print("qqqqqq")
        conn.commit()
        cursor.close()
        conn.close()
        print("qqqq----qqq")
        #최종 리턴
        return jsonify(data)


if __name__ == "__main__":
    app.run(debug=False, host="0.0.0.0", port=5000)

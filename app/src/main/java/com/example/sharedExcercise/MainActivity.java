package com.example.sharedExcercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 입력부분 변수로 지정
    EditText inputTextName;
    EditText inputTextAge;
    Button inputBtn;

    // 출력부분 변수로 지정
    TextView outputTextViewName;
    TextView outputTextViewAge;
    Button outputBtn;

    // SharedPreferences 변수를 설정한다.
    SharedPreferences myData;
    // SharedPreferences를 수정하기 위한 editor변수를 생성한다. (아직 myData와 연결 전)
    SharedPreferences.Editor editMyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activity_main.xml과 MainActivity.java를 연결한다.
        inputTextName = findViewById(R.id.editTextName);
        inputTextAge = findViewById(R.id.editAge);
        inputBtn = findViewById(R.id.saveBtn);
        outputTextViewName = findViewById(R.id.outputTextViewName);
        outputTextViewAge = findViewById(R.id.outputTextViewAge);
        outputBtn = findViewById(R.id.outputBtn);


        // SharedPreferences를 설정한다.
        // 현재 JAVA코드에서만 Users라는 DB(또는 Shared)를 생성합니다. MODE_PRIVATE는 해당 앱만 DB에 접근할수 있습니다.
        myData = getSharedPreferences("Users", Context.MODE_PRIVATE);
        // myData와 연동하여 editMyData를 생성,수정 삭제할수 있게 진행하기
        editMyData = myData.edit();

        // 입력버튼을 누를경우 이름과 나이를 저장한다.
        inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("입력버튼 누름");
                System.out.println("입력한 이름 : " + inputTextName.getText().toString());
                System.out.println("입력한 나이 : " + inputTextAge.getText().toString());
                // 쉐어드에 키값은 user이고 value값이 "이름,나이"로 저장하게 한다. (요청만 했고 저장된 것은 아님)
                editMyData.putString("user", inputTextName.getText().toString() + "," + inputTextAge.getText().toString());
                // 저장 요청을 한다.
                // apply는 하드사용 순서대로 진행후 해당 앱의 차레가 오면 그때 저장.
                // commit는 순서와 상관없이 바로 저장
                editMyData.apply();

                // 해당 텍스트를 초기화 한다.
                inputTextName.setText("");
                inputTextAge.setText("");
            }
        });

        // 출력버튼을 누를경우 이름과 나이가 출력된다.
        outputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("출력버튼 누름");
                // userData에 2개의 원소로(이름, 나이) 구성된 데이터를 쉐어드에서 받는다. 해당 키는 user이다
                String[] userData = myData.getString("user", "noData,noData").split(",");
                System.out.println("불러온 이름 : " + userData[0]);
                System.out.println("불러온 나이 : " + userData[1]);
                // 쉐어드에서 받은 데이터를 TextView에 출력한다.
                outputTextViewName.setText(userData[0].toString());
                outputTextViewAge.setText(userData[1].toString());

            }
        });

    }
}
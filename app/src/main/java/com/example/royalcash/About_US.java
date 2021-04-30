package com.example.royalcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class About_US extends AppCompatActivity {

    EditText to,subject,textMessage;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__u_s);
        to=findViewById(R.id.email_to);
        to.setText("royalcash3586@gmail.com");
        subject=findViewById(R.id.Subject_12);
        textMessage=findViewById(R.id.Message_1);
        button=findViewById(R.id.yes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

    }

    private void sendMail()
    {
       /* String receptList=to.getText().toString().trim();
        String[] recipits=receptList.split("");
        //arifulpub143@gmail.com
        //sasdiasathi007@gmail.com
        String subject1=subject.getText().toString().trim();
        String email=textMessage.getText().toString().trim();
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipits);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject1);
        intent.putExtra(Intent.EXTRA_TEXT,email);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose a client"));*/
        String subject1=subject.getText().toString().trim();
        String email=textMessage.getText().toString().trim();
       /*Intent intent=new Intent(Intent.ACTION_VIEW,
               Uri.parse("mailto:"+to.getText().toString()));
        intent.putExtra(Intent.EXTRA_TEXT,email);
        startActivity(intent);*/

        String to1=to.getText().toString().trim();
        JavaMailAPI javaMailAPI=new JavaMailAPI(About_US.this,to1,subject1,email);
        javaMailAPI.execute();
    }
}
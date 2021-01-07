package com.example.retocomerciales;


import android.os.Bundle;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;
    public class activity_envios extends Activity{
        TextView etEmail;
        TextView etSubject;
        TextView etMessage;
        Button Send;
        Button attachment;
        String email;
        String subject;
        String message;
        Uri URI = null;
        private static final int PICK_FROM_GALLERY = 101;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_envios);
            etEmail = findViewById(R.id.etTo);
            etSubject = findViewById(R.id.etSubject);
            etMessage = findViewById(R.id.etMessage);
            attachment = findViewById(R.id.btAttachment);

            Send = findViewById(R.id.btSend);
            Send.setEnabled(false);
            etEmail.setText("pistachopohone@gmail.com");
            etSubject.setText("Envio semanal de XML");
            etMessage.setText("Envio el XML de Partners semanal adjunto");
            Send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendEmail();
                }
            });
            //attachment button listener
            attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFolder();

                }
            });
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
                URI = data.getData();

            }
        }

        public void sendEmail() {
            try {
                email = etEmail.getText().toString();
                subject = etSubject.getText().toString();
                message = etMessage.getText().toString();

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                if (URI != null) {
                    emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                }
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
                this.startActivity(Intent.createChooser(emailIntent, "Sending email..."));
            } catch (Throwable t) {
                Toast.makeText(this, "Request failed try again: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }
        public void openFolder() {
            Intent intent = new Intent();
            intent.setType("text/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("return-data", true);
            Send.setEnabled(true);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
        }
    }
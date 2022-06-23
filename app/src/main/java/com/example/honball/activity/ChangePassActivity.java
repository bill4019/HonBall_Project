package com.example.honball.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.honball.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends CommonActivity {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_pass);
        findViewById(R.id.btn_changepassword).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_changepassword:
                    Log.e("클릭", " 클릭");
                    changePass();
                    break;
            }
        }
    };

    private void changePass() {
        String password = ((EditText) findViewById(R.id.et_change_pass)).getText().toString();
        String passCheck = ((EditText) findViewById(R.id.et_change_passcheck)).getText().toString();

        final RelativeLayout loaderLayout = findViewById(R.id.loaderLayout);

        if (password.equals(passCheck)) {
            loaderLayout.setVisibility(View.VISIBLE);
            user.updatePassword(password)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                loaderLayout.setVisibility(View.GONE);
                                Log.d(TAG, "User password updated.");
                                Toast.makeText(getApplicationContext(), "비밀번호를 변경했습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePassActivity.this, MemberInfoActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                loaderLayout.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "비밀번호를 변경을 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            loaderLayout.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "비밀번호 확인을 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
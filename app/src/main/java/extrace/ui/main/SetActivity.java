package extrace.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

import extrace.misc.model.UserInfo;

import static android.content.Context.MODE_PRIVATE;

public class SetActivity extends AppCompatActivity {

    Button tvBack;
    Button tvSave;
    TextInputEditText etURL;
    private ExTraceApplication app;
    private UserInfo user;
    SharedPreferences sp;
    SharedPreferences.Editor et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        sp = getPreferences(MODE_PRIVATE);//模式:其他app是否能看到
        et = sp.edit();//修改器
        app = (ExTraceApplication) getApplication();

        tvBack = findViewById(R.id.user_edit_tv_back);
        tvSave = findViewById(R.id.set_tv_save);
        etURL = findViewById(R.id.set_et_url);
        etURL.setText(app.getServerUrl());

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL = etURL.getText().toString();
                app.setServerUrl(URL);
                Toast.makeText(SetActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                //onKeyDown(KeyEvent.KEYCODE_BACK, null);         //返回
                onBackPressed();        //返回
            }
        });
    }
}

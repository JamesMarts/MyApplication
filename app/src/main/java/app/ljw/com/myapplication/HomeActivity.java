package app.ljw.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.RxEncryptTool;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = findViewById(R.id.tv);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            String sign = "123456";
            String data = "hello world!";
            byte[] aa = RxDataTool.hexString2Bytes(data);
            byte[] signByte = RxDataTool.hexString2Bytes(sign);
            byte[] end = RxEncryptTool.encryptAES2Base64(aa, signByte);
            byte[] res = RxEncryptTool.decryptBase64AES(end, signByte);

            String w = RxDataTool.bytes2HexString(res);

            textView.setText(w);
        } catch (Exception e) {
            textView.setText(e.getMessage() + "");
        }

    }
}

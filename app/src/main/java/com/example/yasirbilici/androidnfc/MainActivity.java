package com.example.yasirbilici.androidnfc;


import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //nfcadapter ve textview tanımlandı
    private NfcAdapter nfcAdapter;
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //varsayılan nfcadapter erişildi
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //nfc durumu kontrol edildi ve sonuç kullanıcıya bildirildi.
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "CİHAZINIZ NFC DESTEKLEMIYOR!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "NFC ETKİN DEĞIL!",
                    Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();
        //herhagibir nfc etiketinin algılanıp algılanmadıgı kontrol edildi.
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Toast.makeText(this,
                    "NFC ETİKETİ ALGILANDI",
                    Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            //gelen nfc etiketinin bilgileri okundu ve degikene aktarıldı
            //etiket bilgileri hexadecimal ve decimal olarak alındı.
            if (tag == null) {

            } else {
                String KartBilgisi = "\n";

                byte[] tagId = tag.getId();
                KartBilgisi += "\nhexadecimal : ";
                for (int i = 0; i < tagId.length; i++) {
                    KartBilgisi += Integer.toHexString(tagId[i] & 0xFF);


                }

                long decid = 0;
                long factor = 1;
                for (int i = 0; i < tagId.length; ++i) {
                    long value = tagId[i] & 0xffl;
                    decid += value * factor;
                    factor *= 256l;
                }
                KartBilgisi += "\ndecimal : " + decid + "\n";

                txt = (TextView)findViewById(R.id.textView);
                //kart bilgisi textView'e yazdırıldı.
                txt.setText(KartBilgisi.toString());
            }
        }

    }

}

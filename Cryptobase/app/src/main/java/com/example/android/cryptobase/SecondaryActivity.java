package com.example.android.cryptobase;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SecondaryActivity extends AppCompatActivity {

    EditText inputBox;
    Button enterButton;
    TextView resultTextView;
    String encryptedText;
    String decryptedText;
    String oneTimePadKey = "xmckl";
    TextView keyTextView;
    Button changeKeyButton;
    LinearLayout keyLayout;
    int keyAForAffine = 5;
    int keyBForAffine = 8;
    LinearLayout optionsLayout;
    ImageButton copyButton;
    int viewPagerPosition;
    TextView headingTextView;
    TextView resultHeadingTextView;
    ImageButton shareButton;
    ImageButton useAsPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        inputBox = (EditText) findViewById(R.id.inputbox);
        enterButton = (Button) findViewById(R.id.enter_button);
        resultTextView = (TextView) findViewById(R.id.result_textview);
        keyTextView = (TextView) findViewById(R.id.key_textview);
        changeKeyButton = (Button) findViewById(R.id.change_key_button);
        keyLayout = (LinearLayout) findViewById(R.id.key_layout);
        optionsLayout = (LinearLayout) findViewById(R.id.options_layout);
        copyButton = (ImageButton) findViewById(R.id.copy_button);
        headingTextView = (TextView) findViewById(R.id.heading_text_view);
        resultHeadingTextView = (TextView) findViewById(R.id.result_heading_textview);
        shareButton = (ImageButton) findViewById(R.id.share_button);
        useAsPassword = (ImageButton) findViewById(R.id.use_as_password_button);


        optionsLayout.setVisibility(View.INVISIBLE);
        keyLayout.setVisibility(View.INVISIBLE);

        encryptedText = "";
        decryptedText = "";
        viewPagerPosition = 0;

        Intent intent = getIntent();
        if (intent.getIntExtra("source", 0) == 1) {
            setTitle("One Time Pad Encryption");
            viewPagerPosition = 1;
            keyLayout.setVisibility(View.VISIBLE);
            keyTextView.setText("Key - " + oneTimePadKey);
            headingTextView.setText("Enter text to Encrypt");
            enterButton.setText("Encrypt");
            enterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String encryptText = inputBox.getText().toString().trim();
                    if (TextUtils.isEmpty(encryptText))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Toast.makeText(getApplicationContext(), "Enter a text to be encrpted!!", Toast.LENGTH_SHORT).show();
                        }
                    encryptedText = oneTimePad(encryptText, oneTimePadKey, 1);
                    resultHeadingTextView.setText("The encrypted text is ");
                    resultTextView.setText(encryptedText);
                    inputBox.getText().clear();
                    optionsLayout.setVisibility(View.VISIBLE);

                }
            });


            changeKeyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SecondaryActivity.this, ChangeKeyActivity.class);
                    intent.putExtra("default_key", oneTimePadKey);
                    startActivityForResult(intent, 0);
                }
            });

        } else if (intent.getIntExtra("source", 0) == 2) {
            setTitle("One Time Pad Decryption");
            viewPagerPosition = 1;
            keyLayout.setVisibility(View.VISIBLE);
            keyTextView.setText("Key - " + oneTimePadKey);
            headingTextView.setText("Enter text to Decrypt");
            enterButton.setText("Decrypt");
            enterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String encryptText = inputBox.getText().toString().trim();
                    if (TextUtils.isEmpty(encryptText))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Toast.makeText(getApplicationContext(), "Enter a text to be decrypted!!", Toast.LENGTH_SHORT).show();
                        }
                    decryptedText = oneTimePad(encryptText, oneTimePadKey, -1);
                    resultHeadingTextView.setText("The decrypted text is ");
                    resultTextView.setText(decryptedText);
                    inputBox.getText().clear();

                }
            });

            changeKeyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SecondaryActivity.this, ChangeKeyActivity.class);
                    intent.putExtra("default_key", oneTimePadKey);
                    startActivityForResult(intent, 0);
                }
            });

        } else if (intent.getIntExtra("source", 0) == 3) {
            setTitle("ROT13 Encryption");
            viewPagerPosition = 0;
            headingTextView.setText("Enter text to Encrypt");
            enterButton.setText("Encrypt");
            enterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String encryptText = inputBox.getText().toString().trim();
                    if (TextUtils.isEmpty(encryptText))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Toast.makeText(getApplicationContext(), "Enter a text to be encrpted!!", Toast.LENGTH_SHORT).show();
                        }
                    encryptedText = rot13(encryptText);
                    resultHeadingTextView.setText("The encrypted text is");
                    resultTextView.setText(encryptedText);
                    inputBox.getText().clear();
                    optionsLayout.setVisibility(View.VISIBLE);

                }
            });


        } else if (intent.getIntExtra("source", 0) == 4) {
            setTitle("ROT 13 Decryption");
            viewPagerPosition = 0;
            headingTextView.setText("Enter text to Decrypt");
            enterButton.setText("Decrypt");
            enterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String encryptText = inputBox.getText().toString().trim();
                    if (TextUtils.isEmpty(encryptText))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Toast.makeText(getApplicationContext(), "Enter a text to be decrypted!!", Toast.LENGTH_SHORT).show();
                        }
                    decryptedText = rot13(encryptText);
                    resultHeadingTextView.setText("The decrypted text is");
                    resultTextView.setText(decryptedText);
                    inputBox.getText().clear();

                }
            });

        } else if (intent.getIntExtra("source", 0) == 5) {
            setTitle("Affine Encryption");
            viewPagerPosition = 2;
            keyLayout.setVisibility(View.VISIBLE);
            headingTextView.setText("Enter text to Encrypt");
            keyTextView.setText("Key is:a=" + keyAForAffine + " & b=" + keyBForAffine);
            enterButton.setText("Encrypt");
            enterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String encryptText = inputBox.getText().toString().trim();
                    if (TextUtils.isEmpty(encryptText))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Toast.makeText(getApplicationContext(), "Enter a text to be encrpted!!", Toast.LENGTH_SHORT).show();
                        }
                    encryptedText = affineEncrypt(encryptText, keyAForAffine, keyBForAffine);
                    resultHeadingTextView.setText("The encrypted text is ");
                    resultTextView.setText(encryptedText);
                    inputBox.getText().clear();
                    optionsLayout.setVisibility(View.VISIBLE);

                }
            });


            changeKeyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SecondaryActivity.this, ChangeKeyAffine.class);
                    intent.putExtra("default_key_a", keyAForAffine);
                    intent.putExtra("default_key_b", keyBForAffine);
                    startActivityForResult(intent, 1);
                }
            });

        } else if (intent.getIntExtra("source", 0) == 6) {
            setTitle("Affine Decryption");
            viewPagerPosition = 2;
            keyLayout.setVisibility(View.VISIBLE);
            keyTextView.setText("Key is:a=" + keyAForAffine + " & b=" + keyBForAffine);
            headingTextView.setText("Enter text to Decrypt");
            enterButton.setText("Decrypt");
            enterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String encryptText = inputBox.getText().toString().trim();
                    if (TextUtils.isEmpty(encryptText))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Toast.makeText(getApplicationContext(), "Enter a text to be decrypted!!", Toast.LENGTH_SHORT).show();
                        }
                    decryptedText = affineDecrypt(encryptText, keyAForAffine, keyBForAffine);
                    resultHeadingTextView.setText("The decrypted text is ");
                    resultTextView.setText(decryptedText);
                    inputBox.getText().clear();

                }
            });
            changeKeyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SecondaryActivity.this, ChangeKeyAffine.class);
                    intent.putExtra("default_key_a", keyAForAffine);
                    intent.putExtra("default_key_b", keyBForAffine);
                    startActivityForResult(intent, 1);
                }
            });
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,encryptedText);
                startActivity(shareIntent);
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("label", encryptedText);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "Encrypted text copied to clipboard!", Toast.LENGTH_SHORT).show();
            }
        });

        useAsPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(SecondaryActivity.this,NewEntryActivity.class);
                intent.putExtra("password_text",encryptedText);
                startActivity(intent);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                oneTimePadKey = data.getStringExtra("result");
                keyTextView.setText("Key - " + oneTimePadKey);
            }

        }
        else if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                keyAForAffine = data.getIntExtra("resultKeyA",5);
                keyBForAffine = data.getIntExtra("resultKeyB",8);
                keyTextView.setText("Key is:a=" + keyAForAffine + " & b=" + keyBForAffine);

            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra("viewpager_position",viewPagerPosition);
                NavUtils.navigateUpTo(this,intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    String rot13(String encryptText) {


        int textArrayLength, x;
        char a;
        char[] resultText = new char[encryptText.length()];
        textArrayLength = encryptText.length();
        for (int i = 0; i < textArrayLength; i++) {
            x = encryptText.charAt(i);
            if (x >= 65 && x <= 90) {
                x -= 65;
                resultText[i] = (char) ((((x + 13) % 26) + 65));

            } else if (x >= 97 && x <= 122) {
                x -= 97;
                resultText[i] = (char) ((((x + 13) % 26) + 97));

            } else if (x >= 48 && x <= 57) {
                x -= 48;
                resultText[i] = (char) ((((x + 5) % 10) + 48));


            } else {
                resultText[i] = encryptText.charAt(i);

            }
        }
        return new String(resultText);
    }

    String oneTimePad(String text, String key, int o)
    {
        int i,lengthOfText,keyLength,j,x,y;
        lengthOfText = text.length();
        char[] resultArray = new char[text.length()] ;
        keyLength = key.length();
        for(i = 0;i < lengthOfText; i++)
        {
            j = i % keyLength;
            y = key.charAt(j);
            x = text.charAt(i);
            if((x >= 97) && (x <= 122))
            {
                x -= 97;
                y -= 97;
                resultArray[i] = (char) (pos((((o * y) + x) % 26)) + 97);

            }
            else if((x >= 65) && (x <= 90))
            {
                x -= 65;
                y -= 97;
                resultArray[i] = (char) (pos((((o * y) + x) % 26)) + 65);
            }
            else resultArray[i] = text.charAt(i);
        }
        return new String(resultArray);
    }
    int pos(int num)
    {
        for( ;num < 0;num += 26){}
        return num;
    }


    String affineEncrypt(String inputText,int a,int b)
    {
        int lengthOfInput,x;
        char[] resultArray = new char[inputText.length()];
        lengthOfInput = inputText.length();

        for(int i = 0;i < lengthOfInput; i++)
        {
            x = inputText.charAt(i);
            if(x >= 65 && x <= 90)
            {
                x -= 65;
                resultArray[i] = (char) ((((a * x) + b) % 26) + 65);

            }
            else if(x >= 97 && x <= 122)
            {
                x -= 97;
                resultArray[i] = (char) ((((a * x) + b) % 26) + 97);
            }
            else resultArray[i] = inputText.charAt(i);
        }
    return new String(resultArray);
    }

    String affineDecrypt(String inputText,int a,int b)
    {
        int lengthOfInput,y;
        char[] resultArray = new char[inputText.length()];
        lengthOfInput = inputText.length();

        for(int i = 0;i < lengthOfInput; i++)
        {
            y = inputText.charAt(i);
            if(y >= 65 && y <= 90)
            {
                y -= 65;
                resultArray[i] = (char) (pos((((26 - a) * (y - b)) % 26)) + 65);
            }
            else if(y >= 97 && y <= 122)
            {
                y -= 97;
                resultArray[i] = (char) (pos((((26 - a) * (y - b)) % 26)) + 97);
            }
            else resultArray[i] = inputText.charAt(i);
        }

        return new String(resultArray);
    }


}

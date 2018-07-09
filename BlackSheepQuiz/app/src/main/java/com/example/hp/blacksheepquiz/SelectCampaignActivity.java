package com.example.hp.blacksheepquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectCampaignActivity extends AppCompatActivity implements View.OnClickListener {

    private DBHelper dh;
    private StringBuilder sb;
    ArrayList<ImageButton> stageLogo;
    ArrayList<Integer> stageBlock;
    ArrayList<Integer> stageClear;
    int idStage, select, idCheck;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_campaign);
        stageSetLogo();
        setupStage();

    }

    private void setupStage() {
        for (int i = 0; i < 20;i++) {
            dh.selectById(i + 1);
            retrieveAllRows();
            idStage = Integer.parseInt(sb.toString());
            stageLogo.get(i).setImageResource(idStage);
            stageLogo.get(i).setOnClickListener(this);
        }
    }

    private void retrieveAllRows() {
        List<Integer> idList = dh.getIdList();
        List<Integer> stageList = dh.getStageList();
        Log.v("MainActivity", idList.toString());
        Log.v("MainActivity", stageList.toString());
        sb = new StringBuilder();
        for (Integer name : stageList) {
            sb.append(name);
        }
    }

    private void stageSetLogo() {
        dh = new DBHelper(this);
        stageLogo = new ArrayList<ImageButton>();
        stageLogo.add((ImageButton)findViewById(R.id.lv1));
        stageLogo.add((ImageButton)findViewById(R.id.lv2));
        stageLogo.add((ImageButton)findViewById(R.id.lv3));
        stageLogo.add((ImageButton)findViewById(R.id.lv4));
        stageLogo.add((ImageButton)findViewById(R.id.lv5));
        stageLogo.add((ImageButton)findViewById(R.id.lv6));
        stageLogo.add((ImageButton)findViewById(R.id.lv7));
        stageLogo.add((ImageButton)findViewById(R.id.lv8));
        stageLogo.add((ImageButton)findViewById(R.id.lv9));
        stageLogo.add((ImageButton)findViewById(R.id.lv10));
        stageLogo.add((ImageButton)findViewById(R.id.lv11));
        stageLogo.add((ImageButton)findViewById(R.id.lv12));
        stageLogo.add((ImageButton)findViewById(R.id.lv13));
        stageLogo.add((ImageButton)findViewById(R.id.lv14));
        stageLogo.add((ImageButton)findViewById(R.id.lv15));
        stageLogo.add((ImageButton)findViewById(R.id.lv16));
        stageLogo.add((ImageButton)findViewById(R.id.lv17));
        stageLogo.add((ImageButton)findViewById(R.id.lv18));
        stageLogo.add((ImageButton)findViewById(R.id.lv19));
        stageLogo.add((ImageButton)findViewById(R.id.lv20));
        stageBlock = new ArrayList<Integer>();
        stageBlock.add(R.drawable.lv1);
        stageBlock.add(R.drawable.lv2b);
        stageBlock.add(R.drawable.lv3b);
        stageBlock.add(R.drawable.lv4b);
        stageBlock.add(R.drawable.lv5b);
        stageBlock.add(R.drawable.lv6b);
        stageBlock.add(R.drawable.lv7b);
        stageBlock.add(R.drawable.lv8b);
        stageBlock.add(R.drawable.lv9b);
        stageBlock.add(R.drawable.lv10b);
        stageBlock.add(R.drawable.lv11b);
        stageBlock.add(R.drawable.lv12b);
        stageBlock.add(R.drawable.lv13b);
        stageBlock.add(R.drawable.lv14b);
        stageBlock.add(R.drawable.lv15b);
        stageBlock.add(R.drawable.lv16b);
        stageBlock.add(R.drawable.lv17b);
        stageBlock.add(R.drawable.lv18b);
        stageBlock.add(R.drawable.lv19b);
        stageBlock.add(R.drawable.lv20b);
        stageClear = new ArrayList<Integer>();
        stageClear.add(R.drawable.lv1);
        stageClear.add(R.drawable.lv2);
        stageClear.add(R.drawable.lv3);
        stageClear.add(R.drawable.lv4);
        stageClear.add(R.drawable.lv5);
        stageClear.add(R.drawable.lv6);
        stageClear.add(R.drawable.lv7);
        stageClear.add(R.drawable.lv8);
        stageClear.add(R.drawable.lv9);
        stageClear.add(R.drawable.lv10);
        stageClear.add(R.drawable.lv11);
        stageClear.add(R.drawable.lv12);
        stageClear.add(R.drawable.lv13);
        stageClear.add(R.drawable.lv14);
        stageClear.add(R.drawable.lv15);
        stageClear.add(R.drawable.lv16);
        stageClear.add(R.drawable.lv17);
        stageClear.add(R.drawable.lv18);
        stageClear.add(R.drawable.lv19);
        stageClear.add(R.drawable.lv20);
    }

    @Override
    public void onClick(View v) {

        intent = new Intent(this,StartCampaignActivity.class);
        Bundle bundle = new Bundle();
        if (v == stageLogo.get(0)) {
            select = 1;
            bundle.putInt("level", select);
            intent.putExtra("cpBundle", bundle);
            startActivity(intent);
            finish();
        }
        if (v == stageLogo.get(1)) {
            dh.selectById(2);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(1)) {
                select = 2;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(2)) {
            dh.selectById(3);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(2)) {
                select = 3;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(3)) {
            dh.selectById(4);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(3)) {
                select = 4;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(4)) {
            dh.selectById(5);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(4)) {
                select = 5;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(5)) {
            dh.selectById(6);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(5)) {
                select = 6;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(6)) {
            dh.selectById(7);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(6)) {
                select = 7;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(7)) {
            dh.selectById(8);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(7)) {
                select = 8;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(8)) {
            dh.selectById(9);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(8)) {
                select = 9;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(9)) {
            dh.selectById(10);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(9)) {
                select = 10;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(10)) {
            dh.selectById(11);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(10)) {
                select = 11;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(11)) {
            dh.selectById(12);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(11)) {
                select = 12;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(12)) {
            dh.selectById(13);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(12)) {
                select = 13;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(13)) {
            dh.selectById(14);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(13)) {
                select = 14;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(14)) {
            dh.selectById(15);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(14)) {
                select = 15;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(15)) {
            dh.selectById(16);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(15)) {
                select = 16;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(16)) {
            dh.selectById(17);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(16)) {
                select = 17;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(17)) {
            dh.selectById(18);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(17)) {
                select = 18;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(18)) {
            dh.selectById(19);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(18)) {
                select = 19;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }
        if (v == stageLogo.get(19)) {
            dh.selectById(20);
            retrieveAllRows();
            idCheck = Integer.parseInt(sb.toString());
            if(idCheck == stageClear.get(19)) {
                select = 20;
                bundle.putInt("level", select);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SelectCampaignActivity.this ,"Please clear before stage",Toast.LENGTH_SHORT).show();
            }
        }

    }
}

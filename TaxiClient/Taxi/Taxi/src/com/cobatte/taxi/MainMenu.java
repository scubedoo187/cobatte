package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity{
	
	Button create, join;
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        setTitle("¸Þ´º");
        
        create = (Button)findViewById(R.id.create);
        join = (Button)findViewById(R.id.join);
        
        create.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(MainMenu.this, CreateRoom.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});
        
        join.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(MainMenu.this, RoomList.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});
        
	}
}

package com.cobatte.taxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class JoinActivity extends Activity {
	EditText inputId;
	EditText inputPw;
	EditText inputRePw;
	EditText inputEmail;
	Button checkId;
	Button doneBtn;
	Button clearAll;
	String birthYear;
	String birthMonth;
	String birthDay;
	MsgString messageObj;
	String userInfoStr;
	String checkIdTemp;

	//Magic Number Start
	final int SMALLALPHABET_Head = 65;
	final int SMALLALPHABET_Tail = 91;
	final int BIGALPHABET_Head = 97;
	final int BIGALPHABET_Tail = 123;
	final int NUMBER_Head = 48;
	final int NUMBER_Tail = 58;
	//Magic Number End
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);
		setTitle("회원가입");

		final Config config = new Config(this);

		Spinner year = (Spinner) findViewById(R.id.birth_y);
		Spinner month = (Spinner) findViewById(R.id.birth_m);
		Spinner day = (Spinner) findViewById(R.id.birth_d);

		inputId = (EditText) findViewById(R.id.idcon);
		inputPw = (EditText) findViewById(R.id.pw);
		inputRePw = (EditText) findViewById(R.id.pwcon);
		inputEmail = (EditText) findViewById(R.id.email);
		checkId = (Button) findViewById(R.id.idBtn);
		doneBtn = (Button) findViewById(R.id.done);
		clearAll = (Button) findViewById(R.id.clearAll);

		doneBtn.setEnabled(false);

		ArrayAdapter<CharSequence> adtYear = ArrayAdapter.createFromResource(
				this, R.array.year_arr,
				android.R.layout.simple_spinner_dropdown_item);
		year.setAdapter(adtYear);

		ArrayAdapter<CharSequence> adtMonth = ArrayAdapter.createFromResource(
				this, R.array.month_arr,
				android.R.layout.simple_spinner_dropdown_item);
		month.setAdapter(adtMonth);

		ArrayAdapter<CharSequence> adtDay = ArrayAdapter.createFromResource(
				this, R.array.day_arr,
				android.R.layout.simple_spinner_dropdown_item);
		day.setAdapter(adtDay);

		year.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				birthYear = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		month.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				birthMonth = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		day.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				birthDay = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		checkId.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean specialCharacter = true;
				String idTemp = inputId.getText().toString();
				for (int i = 0; i < idTemp.length(); i++) {
					if ((byte) idTemp.charAt(i) >= SMALLALPHABET_Head
							&& (byte) idTemp.charAt(i) < SMALLALPHABET_Tail) // 영문 소문자 검증.
						specialCharacter = false;
					else if ((byte) idTemp.charAt(i) >= BIGALPHABET_Head
							&& (byte) idTemp.charAt(i) < BIGALPHABET_Tail) // 영문 대문자 검증.
						specialCharacter = false;
					else if ((byte) idTemp.charAt(i) >= NUMBER_Head
							&& (byte) idTemp.charAt(i) < NUMBER_Tail) // 숫자 검증.
						specialCharacter = false;
					else {
						specialCharacter = true;
						break;
					}
				}
				if (inputId.getText().toString().equals("")) {
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder(JoinActivity.this);
					ab.setMessage("아이디를 먼저 입력하세요.");
					ab.setTitle("경고");
					ab.setPositiveButton("확인", null);
					ab.show();
				} else if (specialCharacter == true) {
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder(JoinActivity.this);
					ab.setMessage("아이디는 영문(대)소문자와 숫자만 입력이 가능합니다.");
					ab.setTitle("경고");
					ab.setPositiveButton("확인", null);
					ab.show();
				} else {
					checkIdTemp = inputId.getText().toString();
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder(JoinActivity.this);
					ab.setMessage(checkIdTemp);
					ab.setNegativeButton("중복  확인",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									String idtmp = inputId.getText().toString();
									messageObj = new MsgString();
									SocketThread st = new SocketThread(
											messageObj);
									userInfoStr = "1";
									userInfoStr += "\t";
									userInfoStr += idtmp;
									messageObj.setActivityStr(userInfoStr);

									try {
										if (config.isNetworkAvailable()) {
											try {
												st.start();
											} catch (Exception e) {
												e.printStackTrace();
											}

											while (true) {
												if (messageObj.isThreadChange()) {
													userInfoStr = messageObj
															.getThreadStr();
													System.out.println(userInfoStr);
													if (userInfoStr.equals("1")) { // 중복값
																				// 없을
																				// 때,
														idtmp += "는 사용이 가능합니다.";
														doneBtn.setEnabled(true);
														break;
													} else {
														idtmp += "는 사용이 불가합니다.";
														doneBtn.setEnabled(false);
														break;
													}
												}
											}
											messageObj.setActivityStr("quit");
										} else {
											Toast.makeText(JoinActivity.this,
													"네트워크를 사용할 수 없습니다.",
													Toast.LENGTH_LONG).show();
										}
									} catch (Exception e) {
										Toast.makeText(getApplicationContext(),
												"예기치 않은 오류가 발생했습니다.",
												Toast.LENGTH_LONG).show();
									}

									AlertDialog.Builder ab = null;
									ab = new AlertDialog.Builder(
											JoinActivity.this);
									ab.setMessage(idtmp);
									ab.setNegativeButton("확인", null);
									ab.setTitle("사용 여부");
									ab.show();
								}
							});
					ab.setPositiveButton("취소", null);
					ab.setTitle("등록할 새 아이디의 중복 여부를 체크합니다.");
					ab.show();
				}
			}
		});

		doneBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String idtmp, pwTmp1, pwTmp2, mTmp;
				idtmp = inputId.getText().toString();
				pwTmp1 = inputPw.getText().toString();
				pwTmp2 = inputRePw.getText().toString();
				mTmp = inputEmail.getText().toString();

				if (idtmp.equals(checkIdTemp)) {
					if (idtmp.equals("") || idtmp == null) {
						AlertDialog.Builder ab = null;
						ab = new AlertDialog.Builder(JoinActivity.this);
						ab.setMessage("아이디를 입력해 주세요.");
						ab.setPositiveButton(android.R.string.ok, null);
						ab.setTitle("경고");
						ab.show();
					} else if (pwTmp1.equals("") || pwTmp1 == null) {
						AlertDialog.Builder ab = null;
						ab = new AlertDialog.Builder(JoinActivity.this);
						ab.setMessage("비밀번호를 입력해 주세요.");
						ab.setPositiveButton(android.R.string.ok, null);
						ab.setTitle("경고");
						ab.show();
						inputPw.setFocusable(true);
					} else {
						if (pwTmp2.equals("") || pwTmp2 == null) {
							AlertDialog.Builder ab = null;
							ab = new AlertDialog.Builder(JoinActivity.this);
							ab.setMessage("비밀번호를 확인해 주세요.");
							ab.setPositiveButton(android.R.string.ok, null);
							ab.setTitle("경고");
							ab.show();
							inputRePw.setFocusable(true);
						} else {
							if (mTmp.equals("") || mTmp == null) {
								AlertDialog.Builder ab = null;
								ab = new AlertDialog.Builder(JoinActivity.this);
								ab.setMessage("이메일주소를 입력해 주세요.");
								ab.setPositiveButton(android.R.string.ok, null);
								ab.setTitle("경고");
								ab.show();
								inputEmail.setFocusable(true);
							} else {
								if (!pwTmp1.equals(pwTmp2)) {
									AlertDialog.Builder ab = null;
									ab = new AlertDialog.Builder(
											JoinActivity.this);
									ab.setMessage("비밀번호가 일치하지 않습니다.");
									ab.setPositiveButton(android.R.string.ok,
											null);
									ab.setTitle("경고");
									ab.show();
									inputPw.setFocusable(true);
								} else if (pwTmp1.equals(pwTmp2)) {
									messageObj = new MsgString();
									SocketThread st = new SocketThread(
											messageObj);
									userInfoStr = "2";
									userInfoStr += "\t";
									userInfoStr += idtmp;
									userInfoStr += "\t";
									userInfoStr += pwTmp1;
									messageObj.setActivityStr(userInfoStr);

									if (config.isNetworkAvailable()) {
										try {
											st.start();
										} catch (Exception e) {
											e.printStackTrace();
										}

										while (true) {
											if (messageObj.isThreadChange()) {
												userInfoStr = messageObj
														.getThreadStr();
												if (userInfoStr.equals("quit")) {// 회원가입
																				// 성공
													finish();
													break;
												} else {
													Toast.makeText(
															JoinActivity.this,
															"회원가입 도중 에러가 발생하였습니다.",
															Toast.LENGTH_LONG)
															.show();
												}
											}
										}
									} else {
										Toast.makeText(JoinActivity.this,
												"네트워크를 사용할 수 없습니다.",
												Toast.LENGTH_LONG).show();
									}
								}
							}
						}
					}
				} else {
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder(JoinActivity.this);
					ab.setMessage("먼저 아이디 중복체크를 해주세요.");
					ab.setPositiveButton("확인", null);
					ab.setTitle("경고");
					ab.show();
				}
			}
		});

		clearAll.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				inputId.setText("");
				inputPw.setText("");
				inputRePw.setText("");
				inputEmail.setText("");
			}
		});
	}

	public void onBackPressed() {
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(JoinActivity.this);
		ab.setMessage("회원가입을 그만하시겠습니까?");
		ab.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		ab.setNegativeButton(android.R.string.cancel, null);
		ab.setTitle("이 페이지를 닫으려고 합니다.");
		ab.show();
	}
}

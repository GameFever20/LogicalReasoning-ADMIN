package app.reasoning.logical.admin.quiz.craftystudio.logicalreasoning_admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import utils.FireBaseHandler;
import utils.Questions;

public class DailyQuizActivity extends AppCompatActivity {


    EditText questionName;
    EditText optionA;
    EditText optionB;
    EditText optionC;
    EditText optionD;

    EditText questionExplaination;
    EditText correctAnswer;
    TextView topicName;
    TextView dateName;
    EditText previousYears;


    ProgressDialog dialog;
    Questions questions = new Questions();


    FireBaseHandler fireBaseHandler;

    String name = null;
    // AlertDialog alertDialog;

    ArrayList<String> topiclist;
    ArrayList<String> dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fireBaseHandler = new FireBaseHandler();

        //declaration
        questionName = (EditText) findViewById(R.id.daily_admin_question_name_edittext);
        optionA = (EditText) findViewById(R.id.daily_admin_question_optiona_edittext);
        optionB = (EditText) findViewById(R.id.daily_admin_question_optionb_edittext);
        optionC = (EditText) findViewById(R.id.daily_admin_question_optionc_edittext);
        optionD = (EditText) findViewById(R.id.daily_admin_question_optiond_edittext);

        correctAnswer = (EditText) findViewById(R.id.daily_admin_question_correctanswer_edittext);
        questionExplaination = (EditText) findViewById(R.id.daily_admin_question_explaination_edittext);
        topicName = (TextView) findViewById(R.id.daily_admin_question_topic_edittext);
        dateName = (TextView) findViewById(R.id.daily_admin_question_test_edittext);
        previousYears = (EditText) findViewById(R.id.daily_admin_question_previousYears_edittext);

        topicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTopicListDialog();
            }
        });
        dateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTestListDialog();
            }
        });

        downloadTopicList();
        downloadDateList();
    }

    public void downloadTopicList() {
        fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadTopicList(30, new FireBaseHandler.OnTopiclistener() {
            @Override
            public void onTopicDownLoad(String topic, boolean isSuccessful) {

            }

            @Override
            public void onTopicListDownLoad(final ArrayList<String> topicList, boolean isSuccessful) {

                if (isSuccessful) {

                    DailyQuizActivity.this.topiclist = topicList;
                }
            }

            @Override
            public void onTopicUpload(boolean isSuccessful) {

            }
        });

    }

    public void downloadDateList() {

        //  showDialog();
        fireBaseHandler = new FireBaseHandler();

        fireBaseHandler.downloadDateList(30, new FireBaseHandler.OnTestSerieslistener() {
            @Override
            public void onTestDownLoad(String test, boolean isSuccessful) {

            }

            @Override
            public void onTestListDownLoad(final ArrayList<String> datelist, boolean isSuccessful) {
                if (isSuccessful) {

                    DailyQuizActivity.this.dateList = datelist;

                }
                //hideDialog();
            }

            @Override
            public void onTestUpload(boolean isSuccessful) {

            }
        });
    }

    public void uploadDateName(String s) {
        fireBaseHandler.uploadDate(s, new FireBaseHandler.OnTestSerieslistener() {
            @Override
            public void onTestDownLoad(String test, boolean isSuccessful) {

            }

            @Override
            public void onTestListDownLoad(ArrayList<String> testList, boolean isSuccessful) {

            }

            @Override
            public void onTestUpload(boolean isSuccessful) {

                if (isSuccessful) {

                    Toast.makeText(DailyQuizActivity.this, "Date Name Uploaded", Toast.LENGTH_SHORT).show();
                    downloadDateList();
                }


                //  alertDialog.hide();
            }
        });
    }

    private void clearData() {

        questionName.setText("");
        optionB.setText("");
        optionA.setText("");
        optionC.setText("");
        optionD.setText("");

        questionExplaination.setText("");
        correctAnswer.setText("");
        topicName.setText("");
        dateName.setText("");
        previousYears.setText("");

    }


    public boolean createQuestion() {
        //creting story class object
        if (isEmpty(questionName)) {
            questionName.setError("Title cannot be null");
            return false;
        } else {
            questions.setQuestionName(questionName.getText().toString());
        }

        if (isEmpty(optionA)) {
            optionA.setError("Story cannot be null");
            return false;

        } else {
            questions.setOptionA(optionA.getText().toString());
        }

        if (isEmpty(optionB)) {
            optionB.setError("Story cannot be null");
            return false;

        } else {
            questions.setOptionB(optionB.getText().toString());
        }

        if (isEmpty(optionC)) {
            optionC.setError("Story cannot be null");
            return false;

        } else {
            questions.setOptionC(optionC.getText().toString());
        }

        if (isEmpty(optionD)) {
            optionD.setError("Story cannot be null");
            return false;

        } else {
            questions.setOptionD(optionD.getText().toString());
        }

        if (isEmpty(correctAnswer)) {
            correctAnswer.setError("Story cannot be null");
            return false;

        } else {
            questions.setCorrectAnswer(correctAnswer.getText().toString());
        }
        if (isEmpty(questionExplaination)) {
            questionExplaination.setError("Story cannot be null");
            return false;

        } else {
            questions.setQuestionExplaination(questionExplaination.getText().toString());
        }

        if (isEmpty(previousYears)) {

        } else {
            questions.setPreviousYearsName((previousYears.getText().toString()));
        }

        questions.setPushNotificaton(false);


        //random no generate
        final int min = 1;
        final int max = 1000;
        Random random = new Random();
        final int r = random.nextInt((max - min) + 1) + min;

        questions.setRandomNumber(r);


        questions.setNotificationText("edit me");
        return true;
    }

    private void showDialog() {
        dialog = new ProgressDialog(DailyQuizActivity.this);
        dialog.setMessage("Uploading");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void hideDialog() {
        dialog.cancel();
    }

    public boolean isEmpty(EditText edittext) {

        if (TextUtils.isEmpty(edittext.getText().toString())) {
            return true;
        } else {
            return false;
        }

    }


    public void getNameFromDialog() {
        // get prompts.xml view


        //Custom layouts are discouraged due to the intended use of Snackbars,but this will do your task!
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linearlayout);

        final Snackbar snackbar = Snackbar.make(linearLayout, "Hey Whats Up", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Inflate your custom view with an Edit Text
        LayoutInflater objLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View snackView = objLayoutInflater.inflate(R.layout.dialog_layout, null); // custom_snac_layout is your custom xml

        final EditText editext = (EditText) snackView.findViewById(R.id.username);
        Button button = (Button) snackView.findViewById(R.id.username_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DailyQuizActivity.this, editext.getText().toString(), Toast.LENGTH_SHORT).show();
                snackbar.dismiss();

                uploadDateName(editext.getText().toString());

            }
        });
        layout.addView(snackView, 0);
        snackbar.show();

    }


    public void openTopicListDialog() {

        //  showDialog();

        String[] stockArr = new String[topiclist.size()];
        stockArr = topiclist.toArray(stockArr);

        AlertDialog.Builder builder = new AlertDialog.Builder(DailyQuizActivity.this);
        builder.setTitle("Topic List")
                .setItems(stockArr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        questions.setQuestionTopicName(topiclist.get(which));
                        topicName.setText(topiclist.get(which));
                        Toast.makeText(DailyQuizActivity.this, topiclist.get(which), Toast.LENGTH_SHORT).show();
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        AlertDialog alertdialog = builder.create();
        alertdialog.show();

        // hideDialog();
    }

    public void openTestListDialog() {
        String[] stockArr = new String[dateList.size()];
        stockArr = dateList.toArray(stockArr);
        AlertDialog.Builder builder = new AlertDialog.Builder(DailyQuizActivity.this);
        builder.setTitle("Date List")
                .setItems(stockArr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        questions.setQuestionTestName(dateList.get(which));
                        dateName.setText(dateList.get(which));
                        Toast.makeText(DailyQuizActivity.this, dateList.get(which), Toast.LENGTH_SHORT).show();
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        AlertDialog alertdialog = builder.create();
        alertdialog.show();
    }

    public void uploadQuestions(View view) {
        if (createQuestion()) {

            showDialog();

            fireBaseHandler = new FireBaseHandler();

            fireBaseHandler.uploadQuestion(questions, new FireBaseHandler.OnQuestionlistener() {
                @Override
                public void onQuestionDownLoad(Questions questions, boolean isSuccessful) {

                }

                @Override
                public void onQuestionListDownLoad(ArrayList<Questions> questionList, boolean isSuccessful) {

                }

                @Override
                public void onQuestionUpload(boolean isSuccessful) {

                    if (isSuccessful) {
                        Toast.makeText(DailyQuizActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        clearData();

                    } else {
                        Toast.makeText(DailyQuizActivity.this, "Uploaded not Succesfully", Toast.LENGTH_SHORT).show();

                    }
                    hideDialog();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_date_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_date) {
            getNameFromDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

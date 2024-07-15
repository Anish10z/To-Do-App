package com.codegama.todolistapplication.bottomSheetFragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.codegama.todolistapplication.R;
import com.codegama.todolistapplication.activity.MainActivity;
import com.codegama.todolistapplication.broadcastReceiver.AlarmBroadcastReceiver;
import com.codegama.todolistapplication.database.DatabaseClient;
import com.codegama.todolistapplication.model.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CreateTaskBottomSheetFragment extends BottomSheetDialogFragment {

    EditText addTaskTitle;
    EditText addTaskDescription;
    EditText taskDate;
    EditText taskTime;
    EditText taskEvent;
    Button addTask;

    int taskId;
    boolean isEdit;
    Task task;

    int mYear, mMonth, mDay;
    int mHour, mMinute;

    setRefreshListener setRefreshListener;

    AlarmManager alarmManager;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    MainActivity activity;
    public static int count = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        addTaskTitle = view.findViewById(R.id.addTaskTitle);
        addTaskDescription = view.findViewById(R.id.addTaskDescription);
        taskDate = view.findViewById(R.id.taskDate);
        taskTime = view.findViewById(R.id.taskTime);
        taskEvent = view.findViewById(R.id.taskEvent);
        addTask = view.findViewById(R.id.addTask);

        addTask.setOnClickListener(v -> {
            if (validateFields()) {
                createTask();
            }
        });

        if (isEdit) {
            showTaskFromId();
        }

        taskDate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showDatePickerDialog();
            }
            return true;
        });

        taskTime.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showTimePickerDialog();
            }
            return true;
        });

        return view;
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    taskDate.setText(String.format(Locale.US, "%d-%d-%d", dayOfMonth, (monthOfYear + 1), year));
                    datePickerDialog.dismiss();
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(getActivity(),
                (view, hourOfDay, minute) -> {
                    taskTime.setText(String.format(Locale.US, "%d:%d", hourOfDay, minute));
                    timePickerDialog.dismiss();
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public boolean validateFields() {
        if (addTaskTitle.getText().toString().isEmpty()) {
            Toast.makeText(activity, "Please enter a valid title", Toast.LENGTH_SHORT).show();
            return false;

        }  else {
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void createTask() {
        AsyncTask.execute(() -> {
            Task createTask = new Task();
            createTask.setTaskTitle(addTaskTitle.getText().toString());
            createTask.setTaskDescrption(addTaskDescription.getText().toString());
            createTask.setDate(taskDate.getText().toString());
            createTask.setLastAlarm(taskTime.getText().toString());
            createTask.setEvent(taskEvent.getText().toString());

            if (!isEdit)
                DatabaseClient.getInstance(getActivity()).getAppDatabase()
                        .dataBaseAction()
                        .insertDataIntoTaskList(createTask);
            else
                DatabaseClient.getInstance(getActivity()).getAppDatabase()
                        .dataBaseAction()
                        .updateAnExistingRow(taskId, addTaskTitle.getText().toString(),
                                addTaskDescription.getText().toString(),
                                taskDate.getText().toString(),
                                taskTime.getText().toString(),
                                taskEvent.getText().toString());

            getActivity().runOnUiThread(() -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    createAnAlarm();
                }
                setRefreshListener.refresh();
                Toast.makeText(getActivity(), "Your event is been added", Toast.LENGTH_SHORT).show();
                dismiss();
            });
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createAnAlarm() {
        try {
            String[] items1 = taskDate.getText().toString().split("-");
            String dd = items1[0];
            String month = items1[1];
            String year = items1[2];

            String[] itemTime = taskTime.getText().toString().split(":");
            String hour = itemTime[0];
            String min = itemTime[1];

            Calendar cal = new GregorianCalendar();
            cal.set(Calendar.YEAR, Integer.parseInt(year));
            cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            cal.set(Calendar.MINUTE, Integer.parseInt(min));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            Log.d("CreateTaskBottomSheet", "Setting alarm for " + taskDate.getText().toString() + " " + taskTime.getText().toString());

            Intent alarmIntent = new Intent(activity, AlarmBroadcastReceiver.class);
            alarmIntent.putExtra("TITLE", addTaskTitle.getText().toString());
            alarmIntent.putExtra("DESC", addTaskDescription.getText().toString());
            alarmIntent.putExtra("DATE", taskDate.getText().toString());
            alarmIntent.putExtra("TIME", taskTime.getText().toString());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, count, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            }
            count++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTaskFromId() {
        AsyncTask.execute(() -> {
            task = DatabaseClient.getInstance(getActivity()).getAppDatabase()
                    .dataBaseAction().selectDataFromAnId(taskId);
            getActivity().runOnUiThread(this::setDataInUI);
        });
    }

    private void setDataInUI() {
        addTaskTitle.setText(task.getTaskTitle());
        addTaskDescription.setText(task.getTaskDescrption());
        taskDate.setText(task.getDate());
        taskTime.setText(task.getLastAlarm());
        taskEvent.setText(task.getEvent());
    }

    public void setTaskId(int taskId, boolean isEdit, Context context, MainActivity activity) {
        this.taskId = taskId;
        this.isEdit = isEdit;
        this.activity = activity;
        this.setRefreshListener = (setRefreshListener) context;
    }

    public interface setRefreshListener {
        void refresh();
    }
}

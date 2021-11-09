package com.wegolook.schemalibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SchemaActivity extends AppCompatActivity {

    private static final String TAG = "Schema Activity";
    private Button btn_submit;
    private ArrayList<GroupsItem> groupsItems = new ArrayList<>();
    private int groupPos = -1;
    private int fieldPos = -1;
    private LinearLayout linearLayout;
    private Toolbar toolbar;
    private String imageFilePath;
    private static final int IMAGE_CAPTURE_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schema);
        btn_submit = findViewById(R.id.btn_submit);
        toolbar = findViewById(R.id.toolbar);
        linearLayout = findViewById(R.id.linearLayout);
        setToolbar();


        Gson gson = new Gson();
        SchemaResponse mainResponse = gson.fromJson(loadJSONFromAsset(), SchemaResponse.class);

        Log.d("Schema Response", loadJSONFromAsset());
        Schema schema = mainResponse.getSchema();
        ArrayList<FormDataItem> formDataItem = mainResponse.getFormData();
        ArrayList<ImageDataItem> imageDataItems = mainResponse.getImageData();
        ArrayList<SchemaModel> schemaModelArrayLists = schema.getData().getSchemaManyVersions();
        SchemaModel schemaModel = schemaModelArrayLists.get(0);
        ArrayList<FieldsItem> fields = (ArrayList<FieldsItem>) schemaModel.getFields();
        ArrayList<GroupsItem> groups = (ArrayList<GroupsItem>) schemaModel.getGroups();

        groupsItems.clear();
        groupsItems = filterSchemaData(formDataItem, fields, groups);
        inflateGroups(groupsItems);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SchemaActivity.this, "This is my Toast message!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<GroupsItem> filterSchemaData(ArrayList<FormDataItem> formDataItem, ArrayList<FieldsItem> fields, ArrayList<GroupsItem> groups) {

        ArrayList<GroupsItem> groupsList = new ArrayList<>();

        for (int i = 0; i < formDataItem.size(); i++) {

            for (int j = 0; j < fields.size(); j++) {
                if (formDataItem.get(i).getId().equalsIgnoreCase(fields.get(j).getId())) {

                    String selected = "";
                    // check for checkbox bcz of id
                    if (fields.get(j).getComponent().equalsIgnoreCase(AppConstants.CHECKBOXES) && !formDataItem.get(i).getValue().isEmpty()) {
                        String value = formDataItem.get(i).getValue();
                        String[] selectedCheckbox = value.split(",");
                        ArrayList<Options> options = fields.get(j).getOptions();
                        for (int l = 0; l < options.size(); l++) {
                            for (int m = 0; m < selectedCheckbox.length; m++) {
                                if (options.get(l).getValue().equalsIgnoreCase(selectedCheckbox[m])) {
                                    options.get(l).setChecked(true);
                                    if (selected.isEmpty()) {
                                        selected = options.get(l).getText();
                                    } else {
                                        selected = selected + ", " + options.get(l).getText();
                                    }
                                }
                            }
                        }

                    }  // check for dropdown bcz of id
                    else if (fields.get(j).getComponent().equalsIgnoreCase(AppConstants.DROPDOWN) && !formDataItem.get(i).getValue().isEmpty()) {
                        String value = formDataItem.get(i).getValue();
                        ArrayList<Options> options = fields.get(j).getOptions();
                        for (int l = 0; l < options.size(); l++) {
                            if (options.get(l).getValue().equalsIgnoreCase(value)) {
                                options.get(l).setChecked(true);
                                selected = options.get(l).getText();
                            }
                        }
                    } else {
                        selected = formDataItem.get(i).getValue();
                    }
                    fields.get(j).setDefaultValue(selected);

                }
            }
        }

        for (int i = 0; i < groups.size(); i++) {

            ArrayList<FieldsItem> fieldsItemArrayList = new ArrayList<>();
            for (int j = 0; j < fields.size(); j++) {

                if (groups.get(i).getName().equals(fields.get(j).getGroup())) {
//                    fields.get(j).setGroupPos(i);
                    for (int k = 0; k < fields.get(j).getContexts().size(); k++) {
                        if (fields.get(j).getContexts().get(k).getEnvironment().equals(getResources().getString(R.string.looker_app))) {
                            if (fields.get(j).getContexts().get(k).getRender().equals(getResources().getString(R.string.input))) {
                                fields.get(j).setReadType(false);
                                fieldsItemArrayList.add(fields.get(j));
                            } /*else if (fields.get(j).getContexts().get(k).getRender().equals("read-only")) {
                                fields.get(j).setReadType(true);
                                fieldsItemArrayList.add(fields.get(j));
                            }*/ else if (fields.get(j).getContexts().get(k).getRender().equals(getResources().getString(R.string.hide))) {
                                ArrayList<Conditions.All> all = new ArrayList<>();
                                if (fields.get(j).getContexts().get(k).getConditions().size() > 0) {
                                    all = getConditionData(fields.get(j).getContexts().get(k).getConditions());
                                    if (fields.get(j).getContexts().get(k).getConditions().get(0).getAs().equals(getResources().getString(R.string.input))) {

                                        for (int a = 0; a < fields.size(); a++) {

                                            if (fields.get(a).getId().equalsIgnoreCase(all.get(0).getField())) {

                                                if (fields.get(a).getDefaultValue().equalsIgnoreCase(all.get(0).getValue().get(0))) {

                                                    fields.get(j).setReadType(false);
                                                    fieldsItemArrayList.add(fields.get(j));
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            groups.get(i).setField(fieldsItemArrayList);
        }

        for (int i = 0; i < groups.size(); i++) {
            GroupsItem groupsItem = groups.get(i);
            if (groupsItem.getField().size() > 0) {
                groupsItem.setExpended(false);
                List<FieldsItem> fieldList = groups.get(i).getField();
                for (int j = 0; j < fieldList.size(); j++) {
                    FieldsItem fieldsItem = fieldList.get(j);
                    fieldsItem.setFieldPos(j);
                    fieldList.set(j, fieldsItem);
                }
                groupsItem.setField(fieldList);
                groups.set(i, groupsItem);
                groupsList.add(groups.get(i));
            }

        }

        for (int i = 0; i < groupsList.size(); i++) {
            GroupsItem groupsItem = groupsList.get(i);
            if (groupsItem.getField().size() > 0) {
                List<FieldsItem> fieldList = groupsList.get(i).getField();
                for (int j = 0; j < fieldList.size(); j++) {
                    FieldsItem fieldsItem = fieldList.get(j);
                    fieldsItem.setGroupPos(i);
                    fieldList.set(j, fieldsItem);
                }
                groupsItem.setField(fieldList);
                groupsList.set(i, groupsItem);
            }

        }
        return groupsList;
    }

    private ArrayList<Conditions.All> getConditionData(List<Conditions> conditions) {
        ArrayList<Conditions.All> all = new ArrayList<>();
        if (conditions.size() > 0) {
            for (int i = 0; i < conditions.size(); i++) {

                all = (ArrayList<Conditions.All>) conditions.get(i).getAny().get(i).getAll();


                Log.d("Get_Condtion_Value", all.get(0).getField() + "," + all.get(0).getOperator() + "," + all.get(0).getValue());
            }
        }
        return all;
    }

    private void updateAccordion(ImageView iv) {

        LinearLayout linearLayout = groupsItems.get(groupPos).getChildLayout();
        if (!groupsItems.get(groupPos).isExpended()) {
            for (int i = 1; i < linearLayout.getChildCount(); i++) {
                linearLayout.getChildAt(i).setVisibility(View.GONE);
            }
            iv.setImageResource(R.drawable.down_arrow);
            groupsItems.get(groupPos).setExpended(true);
        } else {
            for (int i = 1; i < linearLayout.getChildCount(); i++) {
                linearLayout.getChildAt(i).setVisibility(View.VISIBLE);
            }
            iv.setImageResource(R.drawable.up_arrow);
            groupsItems.get(groupPos).setExpended(false);
        }
    }

    private void setToolbar() {
        toolbar.setTitle("Report");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void inflateGroups(ArrayList<GroupsItem> groups) {
        for (int i = 0; i < groups.size(); i++) {
            inflateGroupItem(groups.get(i), i);
        }
    }

    private void inflateGroupItem(final GroupsItem groupsItem, final int gPos) {
        LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View group_view = li.inflate(R.layout.group_layout, null);
        LinearLayout group_root_view = group_view.findViewById(R.id.ll_group);
        TextView group_label = group_root_view.findViewById(R.id.tv_group_label);
        final ImageView iv_down = group_root_view.findViewById(R.id.iv_arrow_down);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        group_root_view.setLayoutParams(params);
        group_label.setText(groupsItem.getName());
        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                convertDpToPixel(16),
                convertDpToPixel(0));
        group_view.setBackground(getResources().getDrawable(R.drawable.default_border_empty));

        for (int i = 0; i < groupsItem.getField().size(); i++) {
            if (groupsItem.getField() != null) {
                switch (groupsItem.getField().get(i).getComponent()) {
                    case AppConstants.TEXT_INPUT: {
                        inflateTextInputLayout(groupsItem.getField().get(i), li, group_root_view);
                        break;
                    }
                    case AppConstants.TEXT_AREA: {
                        inflateTextAreaLayout(groupsItem.getField().get(i), li, group_root_view);
                        break;
                    }
                    case AppConstants.DROPDOWN: {
                        inflateDropdownLayout(groupsItem.getField().get(i), li, group_root_view);
                        break;
                    }
                    case AppConstants.LOCATION: {
                        inflateLocationLayout(groupsItem.getField().get(i), li, group_root_view);
                        break;
                    }
                    case AppConstants.TELEPHONE: {
                        inflateTelephoneLayout(groupsItem.getField().get(i), li, group_root_view);
                        break;
                    }
                    case AppConstants.CAMERA: {
                        inflateCameraLayout(groupsItem.getField().get(i), li, group_root_view);
                        break;
                    }
                    case AppConstants.FILE_UPLOAD: {
                        inflateFileUploadLayout(groupsItem.getField().get(i), li, group_root_view);
                        break;
                    }
                    case AppConstants.CHECKBOXES: {
                        inflateCheckboxLayout(groupsItem.getField().get(i), li, group_root_view);
                        break;
                    }
                    case AppConstants.NUMBER_INPUT: {
                        inflateNumberInputLayout(groupsItem.getField().get(i), li, group_root_view);

                    }
                }
            }
        }

        groupsItem.setChildLayout(group_root_view);
        groupsItem.setImageView(iv_down);
        groupPos = gPos;
        linearLayout.addView(group_view);
        updateAccordion(iv_down);
        group_root_view.setOnClickListener(view -> {
            groupPos = gPos;
            updateAccordion(iv_down);
        });
    }

    private void inflateTextInputLayout(FieldsItem fieldsItem, LayoutInflater vi, LinearLayout group_root_view) {
        ArrayList<ValidationsItem> validations = fieldsItem.getValidations();
        ArrayList<PropertiesItem> properties = fieldsItem.getProperties();

        View v = vi.inflate(R.layout.textview_layout, null);
        TextView tv_label = v.findViewById(R.id.tv_label);
        LinearLayout linearLayout = v.findViewById(R.id.ll_textView);
        final EditText et_label_value = v.findViewById(R.id.et_label_value);

        fieldsItem.setView(et_label_value);

        v.setPadding(convertDpToPixel(8), 0, convertDpToPixel(8), 0);

        // placeholder and label value
        for (int i = 0; i < properties.size(); i++) {

            if (properties.get(i).getLabel().equals(getResources().getString(R.string.label))) {
                tv_label.setText(properties.get(i).getValue());
            }
            if (properties.get(i).getLabel().equals(getResources().getString(R.string.placeholder_text))) {
                et_label_value.setHint(properties.get(i).getValue());
            }

        }
        // set text
        if (fieldsItem.getDefaultValue() != null && !fieldsItem.getDefaultValue().isEmpty()) {
            et_label_value.setText(fieldsItem.getDefaultValue());
        }
        // input field validation
        for (int i = 0; i < validations.size(); i++) {
            ValidationsItem validationsItem = validations.get(i);
            if (validationsItem.getEnabled()) {
                String id = validationsItem.getId();
                switch (id) {
                    case "required": {
                        setCompulsoryAsterisk(tv_label, tv_label.getText().toString());
                        break;
                    }
                    case "length-maximum": {
                        if (validationsItem.getValue() != null) {
                            double maxLength = (double) validationsItem.getValue();
                            InputFilter[] filters = new InputFilter[1];
                            filters[0] = new InputFilter.LengthFilter((int) maxLength);
                            et_label_value.setFilters(filters);
                            break;
                        }
                    }
                    case "is-phone": {
                        et_label_value.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    }

                }

            }
        }
        if (fieldsItem.isReadType())
            setViewStateReadOnly(et_label_value);

        group_root_view.addView(v);
    }

    private void inflateNumberInputLayout(FieldsItem fieldsItem, LayoutInflater vi, LinearLayout group_root_view) {
        ArrayList<ValidationsItem> validations = fieldsItem.getValidations();
        ArrayList<PropertiesItem> properties = fieldsItem.getProperties();

        View v = vi.inflate(R.layout.number_input_layout, null);
        TextView tv_label = v.findViewById(R.id.tv_label);
        LinearLayout linearLayout = v.findViewById(R.id.ll_textView);
        final EditText et_label_value = v.findViewById(R.id.et_label_value);

        fieldsItem.setView(et_label_value);

        v.setPadding(convertDpToPixel(8), 0, convertDpToPixel(8), 0);

        // placeholder and label value
        for (int i = 0; i < properties.size(); i++) {

            if (properties.get(i).getLabel().equals(getResources().getString(R.string.label))) {
                tv_label.setText(properties.get(i).getValue());
            }
            if (properties.get(i).getLabel().equals(getResources().getString(R.string.placeholder_text))) {
                et_label_value.setHint(properties.get(i).getValue());
            }

        }
        // set text
        if (fieldsItem.getDefaultValue() != null && !fieldsItem.getDefaultValue().isEmpty()) {
            et_label_value.setText(fieldsItem.getDefaultValue());
        }
        // input field validation
        for (int i = 0; i < validations.size(); i++) {
            ValidationsItem validationsItem = validations.get(i);
            if (validationsItem.getEnabled()) {
                String id = validationsItem.getId();
                switch (id) {
                    case "required": {
                        setCompulsoryAsterisk(tv_label, tv_label.getText().toString());
                        break;
                    }
                    case "amount-greater": {
                        if (validationsItem.getValue() != null) {
                            double maxLength = (double) validationsItem.getValue();
                            InputFilter[] filters = new InputFilter[1];
                            filters[0] = new InputFilter.LengthFilter((int) maxLength);
                            et_label_value.setFilters(filters);
                            break;
                        }
                    }

                    case "amount-less": {
                        if (validationsItem.getValue() != null) {
                            double maxLength = (double) validationsItem.getValue();
                            InputFilter[] filters = new InputFilter[1];
                            filters[0] = new InputFilter.LengthFilter((int) maxLength);
                            et_label_value.setFilters(filters);
                            break;
                        }
                    }


                }

            }
        }
        if (fieldsItem.isReadType())
            setViewStateReadOnly(et_label_value);

        group_root_view.addView(v);
    }

    private void inflateTextAreaLayout(FieldsItem fieldsItem, LayoutInflater vi, LinearLayout group_root_view) {
        ArrayList<ValidationsItem> validations = fieldsItem.getValidations();
        ArrayList<PropertiesItem> properties = fieldsItem.getProperties();

        View v = vi.inflate(R.layout.textview_area, null);
        TextView tv_label = v.findViewById(R.id.tv_label);
        LinearLayout linearLayout = v.findViewById(R.id.ll_textArea);
        final EditText et_label_value = v.findViewById(R.id.et_label_value);

        fieldsItem.setView(et_label_value);

        v.setPadding(convertDpToPixel(8), 0, convertDpToPixel(8), 0);

        // placeholder and label value
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getLabel().equals(getResources().getString(R.string.label))) {
                tv_label.setText(properties.get(i).getValue());
            }
            if (properties.get(i).getLabel().equals(getResources().getString(R.string.placeholder_text))) {
                et_label_value.setHint(properties.get(i).getValue());
            }

        }
        // set text
        if (fieldsItem.getDefaultValue() != null && !fieldsItem.getDefaultValue().isEmpty()) {
            et_label_value.setText(fieldsItem.getDefaultValue());
        }

        // input field validation
        for (int i = 0; i < validations.size(); i++) {
            ValidationsItem validationsItem = validations.get(i);
            if (validationsItem.getEnabled()) {
                String id = validationsItem.getId();
                switch (id) {
                    case "required": {
                        setCompulsoryAsterisk(tv_label, tv_label.getText().toString());
                        break;
                    }
                    case "length-maximum": {
                        if (validationsItem.getValue() != null) {
                            double maxLength = (double) validationsItem.getValue();
                            InputFilter[] filters = new InputFilter[1];
                            filters[0] = new InputFilter.LengthFilter((int) maxLength);
                            et_label_value.setFilters(filters);
                            break;
                        }
                    }
                }

            }
        }

        if (fieldsItem.isReadType())
            setViewStateReadOnly(et_label_value);

        group_root_view.addView(v);

    }

    private void inflateDropdownLayout(final FieldsItem fieldsItem, LayoutInflater vi, LinearLayout group_root_view) {
        ArrayList<ValidationsItem> validations = fieldsItem.getValidations();
        ArrayList<PropertiesItem> properties = fieldsItem.getProperties();

        View v = vi.inflate(R.layout.dropdown_layout, null);
        LinearLayout linearLayout = v.findViewById(R.id.ll_dropdown);
        TextView tv_label = v.findViewById(R.id.tv_label);
        final EditText et_select = v.findViewById(R.id.et_select);
        fieldsItem.setView(et_select);

        v.setPadding(convertDpToPixel(8), 0, convertDpToPixel(8), 0);

        // label value placeholder is 'select' by default
        for (int i = 0; i < properties.size(); i++) {

            if (properties.get(i).getLabel().equals(getResources().getString(R.string.label))) {
                tv_label.setText(properties.get(i).getValue());
            }
        }
        // set text
        if (fieldsItem.getDefaultValue() != null && !fieldsItem.getDefaultValue().isEmpty()) {
            et_select.setText(fieldsItem.getDefaultValue());
        }

        group_root_view.addView(v);

        et_select.setOnClickListener(view -> showDropdownDialog(et_select, fieldsItem.getOptions()));

        for (int i = 0; i < validations.size(); i++) {
            ValidationsItem validationsItem = validations.get(i);
            if (validationsItem.getEnabled()) {
                String id = validationsItem.getId();
                switch (id) {
                    case "required": {
                        setCompulsoryAsterisk(tv_label, tv_label.getText().toString());
                        break;
                    }
                }

            }
        }

        if (fieldsItem.isReadType())
            setViewStateReadOnly(et_select);

    }

    private void inflateCheckboxLayout(final FieldsItem fieldsItem, LayoutInflater vi, LinearLayout group_root_view) {
        ArrayList<ValidationsItem> validations = fieldsItem.getValidations();
        ArrayList<PropertiesItem> properties = fieldsItem.getProperties();

        View v = vi.inflate(R.layout.checkbox_layout, null);
        LinearLayout linearLayout = v.findViewById(R.id.ll_checkbox);
        TextView tv_label = v.findViewById(R.id.tv_label);
        final EditText et_select = v.findViewById(R.id.et_select);

        fieldsItem.setView(et_select);

        v.setPadding(convertDpToPixel(8), 0, convertDpToPixel(8), 0);

        // label value placeholder is 'select' by default
        for (int i = 0; i < properties.size(); i++) {

            if (properties.get(i).getLabel().equals(getResources().getString(R.string.label))) {
                tv_label.setText(properties.get(i).getValue());
            }
        }
        // set text
        if (fieldsItem.getDefaultValue() != null && !fieldsItem.getDefaultValue().isEmpty()) {
            et_select.setText(fieldsItem.getDefaultValue());
        }

        group_root_view.addView(v);

        et_select.setOnClickListener(view -> showCheckboxDialog(et_select, fieldsItem.getOptions()));

        for (int i = 0; i < validations.size(); i++) {
            ValidationsItem validationsItem = validations.get(i);
            if (validationsItem.getEnabled()) {
                String id = validationsItem.getId();
                switch (id) {
                    case "required": {
                        setCompulsoryAsterisk(tv_label, tv_label.getText().toString());
                        break;
                    }
                }

            }
        }

        if (fieldsItem.isReadType())
            setViewStateReadOnly(et_select);

    }

    private void inflateLocationLayout(FieldsItem fieldsItem, LayoutInflater vi, LinearLayout group_root_view) {
        ArrayList<ValidationsItem> validations = fieldsItem.getValidations();
        ArrayList<PropertiesItem> properties = fieldsItem.getProperties();

        View v = vi.inflate(R.layout.location_layout, null);
        TextView tv_label = v.findViewById(R.id.tv_label);
        LinearLayout linearLayout = v.findViewById(R.id.ll_location);
        final EditText et_label_value = v.findViewById(R.id.et_label_value);

        fieldsItem.setView(et_label_value);

        v.setPadding(convertDpToPixel(8), 0, convertDpToPixel(8), 0);

        // placeholder and label value
        for (int i = 0; i < properties.size(); i++) {

            if (properties.get(i).getLabel().equals(getResources().getString(R.string.label))) {
                tv_label.setText(properties.get(i).getValue());
            }
            if (properties.get(i).getLabel().equals(getResources().getString(R.string.placeholder_text))) {
                et_label_value.setHint(properties.get(i).getValue());
            }

        }
        // set text
        if (fieldsItem.getDefaultValue() != null && !fieldsItem.getDefaultValue().isEmpty()) {
            et_label_value.setText(fieldsItem.getDefaultValue());
        }
        // input field validation
        for (int i = 0; i < validations.size(); i++) {
            ValidationsItem validationsItem = validations.get(i);
            if (validationsItem.getEnabled()) {
                String id = validationsItem.getId();
                switch (id) {
                    case "required": {
                        setCompulsoryAsterisk(tv_label, tv_label.getText().toString());
                        break;
                    }

                    case "length-maximum": {
                        if (validationsItem.getValue() != null) {
                            double maxLength = (double) validationsItem.getValue();
                            InputFilter[] filters = new InputFilter[1];
                            filters[0] = new InputFilter.LengthFilter((int) maxLength);
                            et_label_value.setFilters(filters);
                            break;
                        }
                    }
                }

            }
        }

        if (fieldsItem.isReadType())
            setViewStateReadOnly(et_label_value);


        group_root_view.addView(v);

    }

    private void inflateTelephoneLayout(FieldsItem fieldsItem, LayoutInflater vi, LinearLayout group_root_view) {
        ArrayList<PropertiesItem> properties = fieldsItem.getProperties();
        ArrayList<ValidationsItem> validations = fieldsItem.getValidations();

        View v = vi.inflate(R.layout.telephone_layout, null);
        LinearLayout linearLayout = v.findViewById(R.id.ll_telephone);
        TextView tv_label = v.findViewById(R.id.tv_label);
        final EditText et_label_value = v.findViewById(R.id.et_label_value);

        fieldsItem.setView(et_label_value);

        v.setPadding(convertDpToPixel(8), 0, convertDpToPixel(8), 0);

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(new WeakReference<EditText>(et_label_value));
        et_label_value.addTextChangedListener(addLineNumberFormatter);

        // placeholder and label value

        for (int i = 0; i < properties.size(); i++) {

            if (properties.get(i).getLabel().equals(getResources().getString(R.string.label))) {
                tv_label.setText(properties.get(i).getValue());
            }
            if (properties.get(i).getLabel().equals(getResources().getString(R.string.placeholder_text))) {
                et_label_value.setHint(properties.get(i).getValue());
            }

        }
        // set text
        if (fieldsItem.getDefaultValue() != null && !fieldsItem.getDefaultValue().isEmpty()) {
            et_label_value.setText(fieldsItem.getDefaultValue());
        }

        for (int i = 0; i < validations.size(); i++) {
            ValidationsItem validationsItem = validations.get(i);
            if (validationsItem.getEnabled()) {
                String id = validationsItem.getId();
                switch (id) {
                    case "required": {
                        setCompulsoryAsterisk(tv_label, tv_label.getText().toString());
                        break;
                    }
                }

            }
        }


        if (fieldsItem.isReadType())
            setViewStateReadOnly(et_label_value);

        group_root_view.addView(v);

    }

    private void inflateFileUploadLayout(final FieldsItem fieldsItem, LayoutInflater vi, LinearLayout group_root_view) {

        ArrayList<PropertiesItem> properties = fieldsItem.getProperties();
        ArrayList<ValidationsItem> validations = fieldsItem.getValidations();

        View v = vi.inflate(R.layout.fileupload_layout, null);
        TextView tv_label = v.findViewById(R.id.tv_label);
        LinearLayout linearLayout = v.findViewById(R.id.ll_upload);
        final ImageView iv_upload = v.findViewById(R.id.iv_upload);
        final LinearLayout previewLayout = v.findViewById(R.id.ll_preview_layout);
        RelativeLayout fileLayout = v.findViewById(R.id.rr_file_layout);
        TextView tv_replace = v.findViewById(R.id.tv_replace);
        final TextView tv_delete = v.findViewById(R.id.tv_delete);
        tv_replace.setTag(getResources().getString(R.string.replace));
        tv_delete.setTag(getResources().getString(R.string.delete));

        tv_replace.setId(fieldsItem.getIndex());
        tv_delete.setId(fieldsItem.getIndex());

        previewLayout.setVisibility(View.GONE);
        fileLayout.setGravity(View.VISIBLE);

        fieldsItem.setView(iv_upload);
        fieldsItem.setPreviewLayout(previewLayout);
        fieldsItem.setImageLayout(fileLayout);
//        fieldsItem.setFieldRecyclerViewLayout(rv);


        v.setPadding(convertDpToPixel(8), 0, convertDpToPixel(8), 0);

        // placeholder and label value
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getLabel().equals(getResources().getString(R.string.label))) {
                tv_label.setText(properties.get(i).getValue());
            }

        }

        group_root_view.addView(v);


        // input field validation
        for (int i = 0; i < validations.size(); i++) {
            ValidationsItem validationsItem = validations.get(i);
            if (validationsItem.getEnabled()) {
                String id = validationsItem.getId();
                switch (id) {
                    case "required": {
                        setCompulsoryAsterisk(tv_label, tv_label.getText().toString());
                        break;
                    }
                }

            }
        }

        if (fieldsItem.isReadType())
            setViewStateReadOnly(iv_upload);

    }

    private void inflateCameraLayout(final FieldsItem fieldsItem, LayoutInflater vi, LinearLayout group_root_view) {
        ArrayList<PropertiesItem> properties = fieldsItem.getProperties();
        ArrayList<ValidationsItem> validations = fieldsItem.getValidations();


        View v = vi.inflate(R.layout.camera_layout, null);
        TextView tv_label = v.findViewById(R.id.tv_label);
        LinearLayout linearLayout = v.findViewById(R.id.ll_image);
        final ImageView iv_camera = v.findViewById(R.id.iv_camera);
        final ImageView iv_add_camera = v.findViewById(R.id.iv_add_image);
        final LinearLayout previewLayout = v.findViewById(R.id.ll_preview_layout);
        final RelativeLayout imageDescriptionLayout = v.findViewById(R.id.ll_image_description);
        final ImageView iv_edit_description = v.findViewById(R.id.iv_edit_description);
        final TextView tv_description = v.findViewById(R.id.tv_image_Description);
        final TextView tv_required_image_count = v.findViewById(R.id.tv_required_image_count);
        RelativeLayout imageLayout = v.findViewById(R.id.rr_image_layout);
        TextView tv_replace = v.findViewById(R.id.tv_replace);
        final TextView tv_delete = v.findViewById(R.id.tv_delete);
        RecyclerView rv = v.findViewById(R.id.rv_multiple_images);
        rv.setVisibility(View.VISIBLE);
        rv.setLayoutManager(new LinearLayoutManager(this));
        tv_replace.setTag(getResources().getString(R.string.replace));
        tv_delete.setTag(getResources().getString(R.string.delete));


        tv_replace.setId(fieldsItem.getIndex());
        tv_delete.setId(fieldsItem.getIndex());
        iv_edit_description.setId(fieldsItem.getIndex());
        tv_description.setId(fieldsItem.getIndex());


        previewLayout.setVisibility(View.GONE);
        imageDescriptionLayout.setVisibility(View.GONE);
        imageLayout.setGravity(View.VISIBLE);

        iv_camera.setId(fieldsItem.getIndex());
        linearLayout.setId(fieldsItem.getIndex());

        fieldsItem.setView(iv_camera);
        fieldsItem.setPreviewLayout(previewLayout);
        fieldsItem.setImageDescriptionLayout(imageDescriptionLayout);
        fieldsItem.setImageLayout(imageLayout);


        v.setPadding(convertDpToPixel(8), 0, convertDpToPixel(8), 0);

        // placeholder and label value
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getLabel().equals(getResources().getString(R.string.label))) {
                tv_label.setText(properties.get(i).getValue());
                //  new GetAllMultipleSavedImageTask(schemaDao,groupPos,fieldPos,jobId);
            }
        }

        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getLabel().equals(getResources().getString(R.string.multiple_photos))) {
                if (properties.get(i).getValue().equals(getResources().getString(R.string.property_true))) {
                    //iv_add_camera.setVisibility(View.VISIBLE);
                    tv_required_image_count.setVisibility(View.VISIBLE);

                    for (int j = 0; j < validations.size(); j++) {
                        ValidationsItem validationsItem = validations.get(j);
                        if (validationsItem.getId().equals(getResources().getString(R.string.required))) {
                            double value = (Double) validationsItem.getValue();


                            int totalRequiredImage = (int) value;
                            if (validationsItem.getId().equals(getResources().getString(R.string.required)) && validationsItem.getEnabled()) {

                                tv_required_image_count.setText(totalRequiredImage + " Images Mandatory");
                            } else {
                                tv_required_image_count.setText("Add " + totalRequiredImage + " Images");
                            }

                        }
                    }
                    groupPos = fieldsItem.getGroupPos();
                    fieldPos = fieldsItem.getFieldPos();
                }
            }
        }
        group_root_view.addView(v);

        iv_camera.setOnClickListener(view -> {
            openCameraIntent();
        });

        // set image
       /* if (!fieldsItem.getDefaultValue().isEmpty()) {
            final Bitmap[] bmp = {null};
            previewLayout.setVisibility(View.VISIBLE);
            imageLayout.setVisibility(View.GONE);
            if (previewLayout.getChildAt(0) instanceof ImageView) {
                ImageView preview = (ImageView) previewLayout.getChildAt(0);
                Picasso.get().load(fieldsItem.getDefaultValue()).into(preview);
                preview.setOnClickListener(view -> fullScreenshotDialog(SubmitReportActivity.this, fieldsItem.getDefaultValue()));
            }
        } else*/

       /* for (int k = 0; k < properties.size(); k++) {
            if (properties.get(k).getLabel().equals(getResources().getString(R.string.multiple_photos))) {
                if (properties.get(k).getValue().equals(getResources().getString(R.string.property_true))) {
                    if (multipleData != null) {

                        for (int l = 0; l < validations.size(); l++) {
                            ValidationsItem validationsItem = validations.get(l);
                            String id = validationsItem.getId();

                            if (id.equals(getResources().getString(R.string.required))) {
                                double value = (Double) validationsItem.getValue();
                                int totalRequiredImage = (int) value;

                                int addedPics = 0;
                                for (int n = 0; n < multipleData.size(); n++) {
                                    if (multipleData.get(n).getGroupPosition() == groupPos && multipleData.get(n).getFieldPosition() == fieldPos) {
                                        addedPics = addedPics + 1;
                                    }
                                }
                                TextView tv = imageLayout.getChildAt(4).findViewById(R.id.tv_required_image_count);
                                ImageView iv_plus = imageLayout.getChildAt(3).findViewById(R.id.iv_add_image);


                                if (addedPics >= totalRequiredImage) {
                                    //tv.setText(totalRequiredImage - addedPics + " images mendatory");
                                    tv.setVisibility(View.GONE);
                                    iv_plus.setVisibility(View.GONE);
                                }
                            }
                        }
                        imageLayout.getChildAt(0).setVisibility(View.VISIBLE);
                        imageLayout.getChildAt(1).setVisibility(View.VISIBLE);
                        imageLayout.getChildAt(2).setVisibility(View.VISIBLE);
                        groupPos = fieldsItem.getGroupPos();
                        fieldPos = fieldsItem.getFieldPos();
                        for (int j = 0; j < multipleData.size(); j++) {
                            if (multipleData.get(j).getFieldPosition() == fieldPos && multipleData.get(j).getGroupPosition() == groupPos) {
                                list.add(multipleData.get(j));

                                imageLayout.getChildAt(3).setVisibility(View.GONE);
                                previewLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                    if (fieldsItem.getImageArray() != null) {
                        previewLayout.setVisibility(View.VISIBLE);

                        if (fieldsItem.getDescription() == null || fieldsItem.getDescription().equals("")) {
                            imageDescriptionLayout.setVisibility(View.GONE);
                        } else {
                            imageDescriptionLayout.setVisibility(View.VISIBLE);
                        }
                        imageLayout.setVisibility(View.GONE);

                        try {
                            Bitmap bmp = BitmapFactory.decodeByteArray(fieldsItem.getImageArray(), 0, fieldsItem.getImageArray().length);
                            if (previewLayout.getChildAt(0) instanceof ImageView) {
                                ImageView preview = (ImageView) previewLayout.getChildAt(0);
                                int W = bmp.getWidth();
                                int H = bmp.getHeight();
                                if (Build.MANUFACTURER.equalsIgnoreCase(getResources().getString(R.string.samsung))) {
                                    if (H > W) {
                                        preview.setRotation(180);
                                    } else {
                                        preview.setRotation(90);
                                    }
                                }
                                preview.setImageBitmap(bmp);
                                if (fieldsItem.getDescription() != null || !fieldsItem.getDescription().equals("")) {
                                    tv_description.setText(fieldsItem.getDescription());
                                } else {
                                    imageDescriptionLayout.setVisibility(View.GONE);
                                }

                                preview.setOnClickListener(view -> fullScreenshotDialog(SubmitReportActivity.this, bmp));
                            }

                        } catch (Exception exception) {
                            exception.printStackTrace();
                            NewRelic.recordHandledException(exception);
                        }

                    }

                }
            }
        }

        MultipleImageAdapter adapter = new MultipleImageAdapter(this, (ArrayList<MultipleImagesData>) list, (MultipleImageAdapter.OnItemClickListener) this, fieldPos, groupPos);
        rv.setAdapter(adapter);
        iv_camera.setTag(new ArrayList<MultipleImagesData>());


        iv_camera.setOnClickListener(view -> {
            groupPos = fieldsItem.getGroupPos();
            fieldPos = fieldsItem.getFieldPos();

            for (int i = 0; i < properties.size(); i++) {
                if (properties.get(i).getLabel().equals(getResources().getString(R.string.multiple_photos)) && Boolean.parseBoolean(properties.get(i).getValue())) {

                    int totalRequiredImage = 0;
                    for (int j = 0; j < validations.size(); j++) {
                        ValidationsItem validationsItem = validations.get(j);
                        double value = (Double) validationsItem.getValue();
                        totalRequiredImage = (int) value;

                    }
                    int addedPics = 0;
                    if (multipleData != null && multipleData.size() > 0) {

                        for (int n = 0; n < multipleData.size(); n++) {
                            if (multipleData.get(n).getGroupPosition() == groupPos && multipleData.get(n).getFieldPosition() == fieldPos) {
                                addedPics = addedPics + 1;
                            }
                        }
                        if (addedPics < totalRequiredImage) {
                            isFileUpload = false;
                            if (checkAndRequestPermission()) {
                                openCameraIntent();
                            } else {
                                showSnackbar(findViewById(android.R.id.content), "Please Allow Required Permission");
                            }
                        } else {
                            Toast.makeText(this, "Only " + totalRequiredImage + " Images Allowed", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        isFileUpload = false;
                        if (checkAndRequestPermission()) {
                            openCameraIntent();
                        } else {
                            showSnackbar(findViewById(android.R.id.content), "Please Allow Required Permission");
                        }

                    }


                } else if (properties.get(i).getLabel().equals("Multiple Photos") && !Boolean.parseBoolean(properties.get(i).getValue())) {

                    isFileUpload = false;
                    if (checkAndRequestPermission()) {
                        openCameraIntent();
                    } else {
                        showSnackbar(findViewById(android.R.id.content), "Please Allow Required Permission");
                    }
                    groupPos = fieldsItem.getGroupPos();
                    fieldPos = fieldsItem.getFieldPos();

                }
            }
        });

        tv_delete.setOnClickListener(v1 -> {
            isFileUpload = false;
            groupPos = fieldsItem.getGroupPos();
            fieldPos = fieldsItem.getFieldPos();
            List<FieldsItem> fieldsItems = groupsItems.get(groupPos).getField();
            dialog.show();
            schemaViewModel.callDeleteImageApi(fieldsItems.get(fieldPos).getImageId());
        });

        tv_replace.setOnClickListener(v12 -> {
            isFileUpload = false;
            isReplace = true;
            groupPos = fieldsItem.getGroupPos();
            fieldPos = fieldsItem.getFieldPos();

            mSaveSchema = new SaveSchema();
            mSaveSchema.setJobId(jobId);
            mSaveSchema.setComponent(AppConstants.CAMERA);
            mSaveSchema.setImageId(fieldsItem.getImageId());
            mSaveSchema.setGroupPos(fieldsItem.getGroupPos());
            mSaveSchema.setFieldPos(fieldsItem.getFieldPos());
            openCameraIntent();

        });

        iv_add_camera.setOnClickListener(v1 -> {
            for (int i = 0; i < properties.size(); i++) {
                if (properties.get(i).getLabel().equals(getResources().getString(R.string.multiple_photos)) && Boolean.parseBoolean(properties.get(i).getValue())) {

                    int totalRequiredImage = 0;
                    for (int j = 0; j < validations.size(); j++) {
                        ValidationsItem validationsItem = validations.get(i);
                        double value = (Double) validationsItem.getValue();
                        totalRequiredImage = (int) value;
                    }

                    int addedPics = 0;
                    for (int n = 0; n < multipleData.size(); n++) {
                        if (multipleData.get(n).getGroupPosition() == groupPos && multipleData.get(n).getFieldPosition() == fieldPos) {
                            addedPics = addedPics + 1;
                        }
                    }

                    if (addedPics < totalRequiredImage) {
                        Toast.makeText(this, "only " + totalRequiredImage + " image Allow", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    isFileUpload = false;
                    if (checkAndRequestPermission()) {
                        openCameraIntent();
                    } else {
                        showSnackbar(findViewById(android.R.id.content), "Please Allow Required Permission");
                    }
                    groupPos = fieldsItem.getGroupPos();
                    fieldPos = fieldsItem.getFieldPos();

                }
            }

        });

        iv_edit_description.setOnClickListener(v1 -> {
            isUpdateImageDescription = true;
            List<FieldsItem> fieldsItems = groupsItems.get(groupPos).getField();

            updateDescriptionDialog(this, fieldsItem.getDescription(), fieldsItem.getImageId(), fieldsItem.getGroupPos(), fieldsItem.getFieldPos());
        });*/


        // input field validation
        for (int i = 0; i < validations.size(); i++) {
            ValidationsItem validationsItem = validations.get(i);
            if (validationsItem.getEnabled()) {
                String id = validationsItem.getId();
                switch (id) {
                    case "required": {
                        setCompulsoryAsterisk(tv_label, tv_label.getText().toString());
                        break;
                    }
                }

            }
        }

        if (fieldsItem.isReadType())
            setViewStateReadOnly(iv_camera);

    }

    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float density = metrics.density;
        float px = dp * (density);
        return Math.round(px);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.sample);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void setCompulsoryAsterisk(TextView txtName, String txt_name) {
        String colored = " *";
        SpannableStringBuilder strBuilder = new SpannableStringBuilder();
        strBuilder.append(txt_name);
        int start = strBuilder.length();
        strBuilder.append(colored);
        int end = strBuilder.length();
        strBuilder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtName.setText(strBuilder);
    }

    private void setViewStateReadOnly(View view) {
        view.setClickable(false);
        view.setEnabled(false);
        view.setFocusable(false);
        view.setBackground(getResources().getDrawable(R.drawable.default_border_filled));

    }

    private void showDropdownDialog(final EditText editText, final ArrayList<Options> options) {
        final CharSequence[] items;
        if (options != null && options.size() > 0) {
            items = new CharSequence[options.size()];
            for (int i = 0; i < options.size(); i++) {
                items[i] = options.get(i).getText();
            }
        } else {
            items = new CharSequence[]{""};
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        builder.setItems(items, (dialogInterface, i) -> {
            String selected = (String) items[i];
            editText.setText(selected);
        });
        builder.show();
    }

    private void showCheckboxDialog(final EditText editText, final ArrayList<Options> options) {
        // Set up the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        // Add a checkbox list
        final CharSequence[] items;
        final boolean[] checkedItems = new boolean[options.size()];

        if (options != null && options.size() > 0) {
            items = new CharSequence[options.size()];
            for (int i = 0; i < options.size(); i++) {
                items[i] = options.get(i).getText();
                checkedItems[i] = options.get(i).isChecked();
            }
        } else {
            items = new CharSequence[]{""};
        }
        builder.setMultiChoiceItems(items, checkedItems, (dialog, which, isChecked) -> {
            checkedItems[which] = isChecked;
            options.get(which).setChecked(isChecked);
            // The user checked or unchecked a box
        });
        // Add OK and Cancel buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String selected = "";
            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    if (selected.isEmpty())
                        selected = (String) items[i];
                    else
                        selected = selected + "," + items[i];
                }
            }
            editText.setText(selected);
            // The user clicked OK
        });

        // Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void openCameraIntent() {

//        try {
//
//            CameraXtra cameraFragment = new CameraXtra();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.camera_preview_container, cameraFragment, CameraXtra.class.getSimpleName())
//                    .commitNow();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG, "Camera Exception: " + ex);
                // NewRelicUtil.logException(ex);
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                try {
                    Uri photoURI = FileProvider.getUriForFile(this, "com.wegolook.schemasdk"+".provider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(pictureIntent, IMAGE_CAPTURE_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

}
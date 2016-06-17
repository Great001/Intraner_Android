package com.xogrp.tkgz.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.SectionAdapter;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.Widget.TKGZCustomEditDialog;
import com.xogrp.tkgz.model.PersonnelSectionSwipe;

import java.util.ArrayList;

public class PersonnelManageFragment extends AbstractTKGZFragment {
    private TextView mTvSearch;
    private ListView mLvSection;
    private ArrayList<PersonnelSectionSwipe> mSectionList;
    private SectionAdapter mAdapter;
    private TKGZCustomEditDialog.OnCustomEditDialogListener mListener;

    @Override
    protected int getLayoutResId() {
        return R.layout.personnel_manage;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvSearch = (TextView) rootView.findViewById(R.id.tv_search);
        mLvSection = (ListView) rootView.findViewById(R.id.lv_section);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mSectionList = getSectionData();
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnScreenNavigationListener().navigateToSearchEmployeePage();
            }
        });
        mAdapter = new SectionAdapter(getActivity(), this);
        mAdapter.setData(mSectionList);
        mLvSection.setAdapter(mAdapter);
        mTvSearch.addTextChangedListener(watcher);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public String getTransactionTag() {
        return "personnel_manage_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.personnel_manage);
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_personnel_manage;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        MenuItem menuItem = menu.findItem(R.id.menu_add_item);
        if (menuItem != null) {
            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    showEditMessageDialog(R.string.dialog_add_department, getString(R.string.dialog_add_department_text),
                            R.string.dialog_finish, R.string.dialog_cancel, new TKGZCustomDialog.OnDialogActionCallback() {
                                @Override
                                public void onConfirmSelected() {

                                }
                            }, null);
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_add_item:
                showEditMessageDialog(R.string.dialog_add_department, getString(R.string.dialog_add_department_text),
                        R.string.dialog_finish, R.string.dialog_cancel, new TKGZCustomDialog.OnDialogActionCallback() {
                            @Override
                            public void onConfirmSelected() {

                            }
                        }, null);
                return true;
            default:
                return false;
        }
    }

    public void navigateToPersonnelPage(int position) {
        PersonnelSectionSwipe sectionSwipe = mSectionList.get(position);
        getOnScreenNavigationListener().navigateToSectionPersonnelPage(sectionSwipe.getSectionName());
    }

    public void deleteSection(int position) {
        TKGZCustomDialog dialog = new TKGZCustomDialog(getActivity(),
                R.string.dialog_warning,
                R.string.dialog_delete_department, new TKGZCustomDialog.OnDialogActionCallback() {
            @Override
            public void onConfirmSelected() {
                // request
            }
        }, true);
        dialog.show();
    }

    public void editSectionName(int position) {
        PersonnelSectionSwipe sectionSwipe = mSectionList.get(position);
        TKGZCustomEditDialog dialog = new TKGZCustomEditDialog(getActivity(),
                R.string.dialog_edit_department,
                sectionSwipe.getSectionName(),
                R.string.dialog_finish,
                R.string.dialog_cancel,
                new TKGZCustomDialog.OnDialogActionCallback() {
                    @Override
                    public void onConfirmSelected() {
                        //request
                    }
                }, null);
        dialog.show();
    }

    private ArrayList<PersonnelSectionSwipe> getSectionData() {
        PersonnelSectionSwipe s1 = new PersonnelSectionSwipe(001L, getString(R.string.txt_personnel_dept), 6);
        PersonnelSectionSwipe s2 = new PersonnelSectionSwipe(002L, getString(R.string.txt_finance_dept), 4);
        PersonnelSectionSwipe s3 = new PersonnelSectionSwipe(003L, getString(R.string.txt_research_and_development_center), 60);
        PersonnelSectionSwipe s4 = new PersonnelSectionSwipe(004L, getString(R.string.txt_marketing_dept), 3);
        PersonnelSectionSwipe s5 = new PersonnelSectionSwipe(005L, getString(R.string.txt_sales_dept), 10);
        ArrayList<PersonnelSectionSwipe> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        return list;
    }
}

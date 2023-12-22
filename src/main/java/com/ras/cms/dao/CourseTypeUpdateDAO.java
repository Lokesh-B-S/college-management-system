package com.ras.cms.dao;

import com.ras.cms.domain.CourseType;

public class CourseTypeUpdateDAO {


        public enum Action {
            UPDATE,
            DELETE
        }

        private Action action;
        private CourseType data;

        public CourseTypeUpdateDAO() {}

        public CourseTypeUpdateDAO.Action getAction() {
            return action;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        public CourseType getData() {
            return data;
        }

        public void setData(CourseType data) {
            this.data = data;
        }
    }



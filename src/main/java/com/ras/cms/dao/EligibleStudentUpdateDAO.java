package com.ras.cms.dao;
import com.ras.cms.domain.EligibleStudent;

public class EligibleStudentUpdateDAO {

        public enum Action {
            UPDATE,
            DELETE
        }

        private Action action;
        private EligibleStudent data;

        public EligibleStudentUpdateDAO() {}

        public Action getAction() {
            return action;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        public EligibleStudent getData() {
            return data;
        }

        public void setData(EligibleStudent data) {
            this.data = data;
        }
    }



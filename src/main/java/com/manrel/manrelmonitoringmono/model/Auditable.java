package com.manrel.manrelmonitoringmono.model;

import com.manrel.manrelmonitoringmono.entity.Audit;

public interface Auditable {

    Audit getAudit();

    void setAudit(Audit audit);
}

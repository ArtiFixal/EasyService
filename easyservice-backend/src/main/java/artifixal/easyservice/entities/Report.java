package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity class representing {@link Repair} report.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="reports")
@Data
@EqualsAndHashCode(callSuper=true)
public class Report extends BaseEntity{

    /**
     * Repair to which report relates.
     */
    @ManyToOne
    @JoinColumn(name="repairID",nullable=false)
    private Repair repair;
    
    /**
     * Date when report was created.
     */
    @Column(nullable=false,updatable=false)
    public Timestamp reportDate;
    
    @Column(nullable=false)
    public Timestamp lastEdited;
    
    @Column(columnDefinition="TEXT",nullable=false)
    public String content;

    public Report(Long id,Repair repair,Timestamp raportDate,String content){
        super(id);
        this.repair=repair;
        this.reportDate=raportDate;
        this.content=content;
    }
}

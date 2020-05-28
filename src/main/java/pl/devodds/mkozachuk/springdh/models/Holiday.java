package pl.devodds.mkozachuk.springdh.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "holidays")
public class Holiday {

    public Holiday(Place place) {
        this.place = place;
    }

    public Holiday() {

    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long holiday_id;

//    @NotBlank(message = "Name is required")
    @Size(min = 5, message = "Name must be at least 5 characters long")
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn
    private User user;

//    @NotBlank(message = "Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

//    @NotBlank(message = "Place is required")
    @ManyToOne()
    @JoinColumn(name = "place_id")
    private Place place;


    @Transient
    private Weather weather;

    //dreamed only
    @Column(name = "price")
    private BigDecimal price;

    private BigDecimal startCapital;
    private BigDecimal monthlySave;

    @Transient
    private List<String> imgs;



    @ManyToOne()
    @JoinColumn()
    private Transport transport;



//    @Transient
//    List<Things> baggage = new ArrayList<>();

    public void addDesign(Place place) {
        this.place = place;
    }
}


package com.blogging.PJBlogging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private @Id Long id;
    private String imgName;
    private String type;
    private String usageStr;
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}

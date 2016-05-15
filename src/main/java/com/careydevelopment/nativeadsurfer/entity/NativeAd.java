package com.careydevelopment.nativeadsurfer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "native_ad")
public class NativeAd {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="days_seen")
	private int daysSeen;

	@Column(name="url")
	private String url;
	
	@Column(name="headline")
	private String headline;
	
	@Column(name="image_url")
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name="company")
	private AdCompany adCompany;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="nativeAd")
	//private List<DomainAd> domainAds;
	

	public NativeAd() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public AdCompany getAdCompany() {
		return adCompany;
	}

	public void setAdCompany(AdCompany adCompany) {
		this.adCompany = adCompany;
	}

	public int getDaysSeen() {
		return daysSeen;
	}

	public void setDaysSeen(int daysSeen) {
		this.daysSeen = daysSeen;
	}
	
}

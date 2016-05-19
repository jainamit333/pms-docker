package com.mmt.propclient.notifier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mmt.adminui.pojo.SingleObNotificationResponse;
import com.mmt.entity.Subscription;

import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class PropertyChangeNotifierTask implements OnSubscribe<SingleObNotificationResponse> {
	private Subscription subscription;

	public PropertyChangeNotifierTask(Subscription sub) {
		this.subscription = sub;
	}

	@Override
	public void call(Subscriber<? super SingleObNotificationResponse> subscriber) {
		String observerIp=subscription.getObserver().getIp();
		try{
			SingleObNotificationResponse resp=new SingleObNotificationResponse();
			String url = new StringBuilder(subscription.getPrefix()).append(observerIp)
					.append(subscription.getSuffix()).toString();
			HttpComponentsClientHttpRequestFactory requestFactory=new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(40000);
			requestFactory.setConnectTimeout(40000);
	    	RestTemplate resttemplate = new RestTemplate(requestFactory);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("qualifier",
					subscription.getSubsQualifier());
			ResponseEntity<ReloadResponse> response = resttemplate.getForEntity(builder.build().encode().toUri(), ReloadResponse.class);
			HttpStatus statuscode=response.getStatusCode();
			if (statuscode != HttpStatus.OK){
				resp.setObserver(observerIp);
			    resp.setError("Response Code "+statuscode);	
			}
			subscriber.onNext(resp);
			subscriber.onCompleted();
		}catch(Throwable e){
			SingleObNotificationResponse resp=new SingleObNotificationResponse(e);
			resp.setObserver(observerIp);
			resp.setError(e.getMessage());
			subscriber.onError(resp);
		}
		
	}

}

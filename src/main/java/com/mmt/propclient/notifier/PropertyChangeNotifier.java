package com.mmt.propclient.notifier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mmt.adminui.pojo.SingleObNotificationResponse;
import com.mmt.entity.Subscription;
import com.mmt.repositories.SubscriptionRepository;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@Component
public class PropertyChangeNotifier {

	@Autowired
	SubscriptionRepository subscriptionRepo;

	public Observable<SingleObNotificationResponse> notifyObservers(Integer fileID, List<String> observerList) {
		List<Subscription> subs;
		if (observerList != null && !observerList.isEmpty()) {
			subs = subscriptionRepo.findByFilesIdAndObserverIpIn(fileID, observerList);
		} else {
			subs = subscriptionRepo.findByFilesId(fileID);
		}
		return Observable.mergeDelayError(
				Observable.from(subs).map(new Func1<Subscription, Observable<SingleObNotificationResponse>>() {

					@Override
					public Observable<SingleObNotificationResponse> call(Subscription subscription) {
						return Observable.create(new PropertyChangeNotifierTask(subscription))
								.subscribeOn(Schedulers.io());
					}
				}));
	}
}

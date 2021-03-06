/*
 * Copyright 2012 Rui Araújo, Luís Fonseca
 *
 * This file is part of Router Keygen.
 *
 * Router Keygen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Router Keygen is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Router Keygen.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.exobel.routerkeygen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.util.Log;

public class AutoConnectManager extends BroadcastReceiver {
	private final onConnectionListener listener;

	public AutoConnectManager(onConnectionListener listener) {
		this.listener = listener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		SupplicantState state = intent
				.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
		if (state != null) {
			if (BuildConfig.DEBUG)
				Log.d(this.getClass().getSimpleName(), state.name());
			if (state.equals(SupplicantState.COMPLETED)) {
				listener.onSuccessfulConection();
				return;
			}
			if (state.equals(SupplicantState.DISCONNECTED)) {
				listener.onFailedConnection();
			}
		}

	}

	public interface onConnectionListener {
		void onFailedConnection();

		void onSuccessfulConection();
	}

}
